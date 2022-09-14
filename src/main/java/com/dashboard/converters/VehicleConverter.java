package com.dashboard.converters;

import com.dashboard.domain.Vehicle;
import com.dashboard.dto.VehicleDto;
import com.dashboard.domain.Driver;

import java.util.stream.Collectors;

public class VehicleConverter {

    public static VehicleDto toVehicleDto(Vehicle vehicle) {
        return VehicleDto.builder()
                .id(vehicle.getId())
                .price(vehicle.getPrice())
                .color(vehicle.getColor())

                .modelId(vehicle.getModel().getId())
                .brand(vehicle.getModel().getBrand())
                .type(vehicle.getModel().getType())
                .tankCapacityInLiters(vehicle.getModel().getTankCapacityInLiters())
                .loadCapacityInTons(vehicle.getModel().getLoadCapacityInTons())
                .seats(vehicle.getModel().getSeats())

                .enterpriseId(vehicle.getEnterprise().getId())
                .enterpriseTimeZone(vehicle.getEnterprise().getTimeZone())

                .drivers(vehicle.getDrivers().stream()
                        .map(Driver::getId)
                        .collect(Collectors.toSet()))
                .purchaseDateTime(vehicle.getPurchaseDateTime())
                .build();
    }
}