package com.dashboard.domain.reports;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.*;


@Getter
@Setter
@NoArgsConstructor
public class VehicleMileageReport extends Report {
    public VehicleMileageReport(String period, ZonedDateTime startDate, ZonedDateTime endDate, Map<LocalDate, Double> results) {
        super(ReportType.VEHICLE_MILEAGE, period, startDate, endDate, results);
    }
}