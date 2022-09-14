package com.dashboard.controller;

import com.dashboard.dto.GPSPointDto;
import com.dashboard.service.TrackService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/tracks")
public class TrackController {

    final static DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    private final TrackService trackService;

    @GetMapping("/vehicles/{vehicleId}")
    List<GPSPointDto> getTrack(@PathVariable("vehicleId") Long vehicleId,
                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime from,
                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime to) {
        return trackService.getTrack(vehicleId, from, to);
    }
}