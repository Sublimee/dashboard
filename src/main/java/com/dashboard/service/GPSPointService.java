package com.dashboard.service;

import com.dashboard.domain.Vehicle;
import com.dashboard.dto.GPSPointDto;
import com.dashboard.repository.VehicleRepository;
import com.dashboard.domain.GPSPoint;
import com.dashboard.domain.Trip;
import com.dashboard.repository.GPSPointRepository;
import com.dashboard.repository.TripRepository;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.impl.CoordinateArraySequence;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ThreadLocalRandom;

import static com.dashboard.converters.GPSPointConverter.toGPSPoint;
import static com.dashboard.converters.GPSPointConverter.toGPSPointDto;

@Slf4j
@AllArgsConstructor
@Service
public class GPSPointService {

    private final GPSPointRepository repository;
    private final VehicleRepository vehicleRepository;
    private final TripRepository tripRepository;

    public GPSPointDto save(GPSPointDto gpsPointDto) {
        Vehicle vehicle = vehicleRepository.findById(gpsPointDto.getVehicleId()).orElseThrow();
        ZoneId enterpriseZoneId = vehicle.getEnterprise().getTimeZone().toZoneId();

        return toGPSPointDto(repository.save(toGPSPoint(gpsPointDto, vehicle, enterpriseZoneId)), enterpriseZoneId, null);
    }

    // @PostConstruct
    public void initPoints() {
        double x = 59.863528;
        double y = 30.23842;

        for (long j = 2L; j <= 10L; j++) {
            Vehicle vehicle = vehicleRepository.findById(j).orElseThrow(IllegalArgumentException::new);

            ZonedDateTime now = ZonedDateTime.now();
            for (int i = 0; i < 1051200; i = i + 10) {
                x = x + 0.00001 * ThreadLocalRandom.current().nextInt(-25, 25);
                y = y + 0.00001 * ThreadLocalRandom.current().nextInt(-25, 25);

                Point point = new Point(new CoordinateArraySequence(new Coordinate[]{new Coordinate(x, y)}), new GeometryFactory());
                GPSPoint gpsPoint = GPSPoint.builder()
                        .point(point)
                        .timestamp(now.plusMinutes(i))
                        .vehicle(vehicle)
                        .build();

                repository.save(gpsPoint);
            }
        }
    }

    // @PostConstruct
    public void initTrips() {
        ZonedDateTime now = ZonedDateTime.now();

        ZonedDateTime from = now;
        ZonedDateTime to = now.plusHours(17);


        for (int i = 1; i <= 1000; i++) {
            Vehicle vehicle = vehicleRepository
                    .findById(ThreadLocalRandom.current().nextLong(1, 11))
                    .orElseThrow(IllegalArgumentException::new);
            tripRepository.save(Trip.builder().startDate(from).endDate(to).vehicle(vehicle).build());
            from = to.plusMinutes(15);
            to = from.plusHours(17);
        }
    }
}