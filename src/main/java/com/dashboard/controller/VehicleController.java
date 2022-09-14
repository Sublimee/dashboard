package com.dashboard.controller;

import com.dashboard.converters.VehicleConverter;
import com.dashboard.domain.PageEntity;
import com.dashboard.dto.VehicleDto;
import com.dashboard.service.VehicleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    @GetMapping("/enterprises/{enterpriseId}")
    public PageEntity<VehicleDto> findAllByEnterpriseId(@PathVariable Long enterpriseId, @RequestParam int page, @RequestParam int size) {
        return vehicleService.findAllByEnterpriseId(enterpriseId, page, size);
    }

    @GetMapping("/{id}")
    public VehicleDto findById(@PathVariable Long id) {
        return VehicleConverter.toVehicleDto(vehicleService.findById(id));
    }

    @PostMapping
    public VehicleDto saveVehicle(@RequestBody VehicleDto vehicleDto) {
        return vehicleService.update(vehicleDto);
    }

    @DeleteMapping("/{id}")
    public void deleteVehicle(@PathVariable("id") Long id) {
        vehicleService.deleteById(id);
    }
}