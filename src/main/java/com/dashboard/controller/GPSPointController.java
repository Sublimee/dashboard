package com.dashboard.controller;

import com.dashboard.dto.GPSPointDto;
import com.dashboard.service.GPSPointService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/points")
public class GPSPointController {

    private final GPSPointService gpsPointService;

    @PostMapping
    GPSPointDto savePoint(@RequestBody GPSPointDto gpsPointDto) {
        return gpsPointService.save(gpsPointDto);
    }
}