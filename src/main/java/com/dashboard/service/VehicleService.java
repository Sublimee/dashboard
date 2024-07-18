package com.dashboard.service;


import com.dashboard.converters.VehicleConverter;
import com.dashboard.domain.Driver;
import com.dashboard.domain.Enterprise;
import com.dashboard.domain.PageEntity;
import com.dashboard.domain.Vehicle;
import com.dashboard.dto.VehicleDto;
import com.dashboard.repository.VehicleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.dashboard.converters.VehicleConverter.toVehicleDto;

@Slf4j
@AllArgsConstructor
@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final EnterpriseService enterpriseService;
    private final ModelService modelService;
    private final SessionFactory sessionFactory;

    public PageEntity<VehicleDto> findAllByEnterpriseId(Long enterpriseId, int page, int size) {
        Page<Vehicle> vehiclesByEnterpriseIdIn = vehicleRepository.findAllByEnterpriseIdIn(
                Set.of(enterpriseId),
                PageRequest.of(page, size)
        );
        List<VehicleDto> vehicleDtos = vehiclesByEnterpriseIdIn.getContent().stream()
                .map(VehicleConverter::toVehicleDto)
                .collect(Collectors.toList());

        return new PageEntity<>(
                vehicleDtos,
                vehiclesByEnterpriseIdIn.getTotalElements(),
                vehiclesByEnterpriseIdIn.getNumber(),
                vehiclesByEnterpriseIdIn.getSize());
    }

    public Vehicle findById(Long id) {
        return vehicleRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        vehicleRepository.findById(id).ifPresent(vehicle -> vehicle.setDrivers(new HashSet<>()));
        vehicleRepository.deleteById(id);
    }

    public VehicleDto update(VehicleDto vehicleDto) {
        Enterprise enterprise = enterpriseService.findById(vehicleDto.getEnterpriseId());

        Vehicle vehicle;
        if (vehicleDto.getId() == null) {
            vehicle = new Vehicle();
            vehicle.setPurchaseDateTime(ZonedDateTime.now().withZoneSameInstant(enterprise.getTimeZone().toZoneId()));
        } else {
            vehicle = findById(vehicleDto.getId());
        }

        vehicle.setPrice(vehicleDto.getPrice());
        vehicle.setColor(vehicleDto.getColor());
        vehicle.setModel(modelService.findById(vehicleDto.getModelId()));
        vehicle.setEnterprise(enterprise);

        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            log.info("Update vehicle transaction begin");
            transaction = session.beginTransaction();
            List<Driver> drivers = session.createQuery("from Driver where id in :ids", Driver.class)
                    .setParameter("ids", vehicleDto.getDrivers())
                    .getResultList();
            vehicle.setDrivers(new HashSet<>(drivers));

            session.saveOrUpdate(vehicle);
            for (Driver driver : drivers) {
                driver.setVehicle(vehicle);
                session.saveOrUpdate(driver);
            }

            transaction.commit();
            log.info("Update vehicle transaction end");
            return toVehicleDto(vehicle);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }
}