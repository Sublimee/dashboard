package com.dashboard.converters;

import com.dashboard.domain.GPSPoint;
import com.dashboard.domain.Vehicle;
import com.dashboard.dto.GPSPointDto;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.impl.CoordinateArraySequence;
import org.jetbrains.annotations.NotNull;

import java.time.ZoneId;
import java.util.*;

public class GPSPointConverter {
    public static GPSPoint toGPSPoint(GPSPointDto gpsPointDto, Vehicle vehicle, ZoneId enterpriseZoneId) {
        return GPSPoint.builder()
                .point(new Point(
                                new CoordinateArraySequence(
                                        new Coordinate[]{new Coordinate(
                                                Double.parseDouble(gpsPointDto.getLatitude()),
                                                Double.parseDouble(gpsPointDto.getLongitude())
                                        )
                                        }),
                                new GeometryFactory()
                        )
                )
                .timestamp(gpsPointDto.getTimestamp().withZoneSameInstant(enterpriseZoneId))
                .vehicle(vehicle)
                .build();
    }

    public static GPSPointDto toGPSPointDto(GPSPoint gpsPoint, ZoneId enterpriseZoneId, String description) {
        return GPSPointDto.builder()
                .id(gpsPoint.getId())
                .vehicleId(gpsPoint.getVehicle().getId())
                .timestamp(gpsPoint.getTimestamp().withZoneSameInstant(enterpriseZoneId))
                .latitude(format(gpsPoint.getPoint().getY()))
                .longitude(format(gpsPoint.getPoint().getX()))
                .description(description)
                .build();
    }

    @NotNull
    private static String format(double gpsPoint) {
        return String.format(Locale.US, "%.6f", gpsPoint);
    }
}
