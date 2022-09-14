package com.dashboard.controller;

import com.dashboard.domain.PageEntity;
import com.dashboard.dto.GPSPointDto;
import com.dashboard.dto.TripDto;
import com.dashboard.service.TripService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/trips")
public class TripController {

    private final TripService tripService;

    @GetMapping("/{tripId}")
    List<GPSPointDto> getTrip(@PathVariable("tripId") Long tripId) {
        return tripService.getTrip(tripId);
    }

    @GetMapping("/vehicles/{vehicleId}/merge")
    List<GPSPointDto> getMergedTracks(@PathVariable("vehicleId") Long vehicleId,
                                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime from,
                                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime to) {
        return tripService.getMergedTrips(vehicleId, from, to);
    }

    @GetMapping("/vehicles/{vehicleId}")
    PageEntity<TripDto> getTrips(@PathVariable("vehicleId") Long vehicleId,
                                 @RequestParam int page,
                                 @RequestParam int size) {
        return tripService.getTrips(vehicleId, page, size);
    }
}