package com.dashboard.service;


import com.dashboard.domain.PageEntity;
import com.dashboard.domain.Vehicle;
import com.dashboard.dto.VehicleDto;
import com.dashboard.converters.VehicleConverter;
import com.dashboard.domain.Driver;
import com.dashboard.domain.Enterprise;
import com.dashboard.repository.DriverRepository;
import com.dashboard.repository.ManagerRepository;
import com.dashboard.repository.VehicleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.dashboard.converters.VehicleConverter.toVehicleDto;

@Slf4j
@AllArgsConstructor
@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final EnterpriseService enterpriseService;
    private final DriverRepository driverRepository;
    private final ModelService modelService;
    private final ManagerRepository managerRepository;

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

        List<Driver> drivers = driverRepository.findAllByIdIn(new ArrayList<>(vehicleDto.getDrivers()));
        vehicle.setDrivers(new HashSet<>(drivers));

        Vehicle updatedVehicle = vehicleRepository.save(vehicle);
        drivers.forEach(x -> x.setVehicle(updatedVehicle));
        driverRepository.saveAll(drivers);

        return toVehicleDto(updatedVehicle);
    }
}