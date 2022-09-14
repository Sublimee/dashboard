package com.dashboard.service;

import com.dashboard.Constants;
import com.dashboard.domain.Vehicle;
import com.dashboard.dto.GPSPointDto;
import com.dashboard.repository.VehicleRepository;
import com.dashboard.repository.GPSPointRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.dashboard.converters.GPSPointConverter.toGPSPointDto;

@AllArgsConstructor
@Service
public class TrackService {

    private final GPSPointRepository repository;
    private final VehicleRepository vehicleRepository;

    public List<GPSPointDto> getTrack(Long vehicleId, ZonedDateTime from, ZonedDateTime to) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow();

        ZoneId enterpriseZoneId = vehicle.getEnterprise().getTimeZone().toZoneId();
        ZonedDateTime utcFrom = from.withZoneSameInstant(Constants.UTC);
        ZonedDateTime utcTo = to.withZoneSameInstant(Constants.UTC);

        return repository.findGPSPointByVehicleIdAndTimestampBetweenOrderByTimestampAsc(vehicleId, utcFrom, utcTo)
                .stream()
                .map(point -> toGPSPointDto(point, enterpriseZoneId, null))
                .collect(Collectors.toList());
    }
}