package com.dashboard.service;

import com.dashboard.domain.GPSPoint;
import com.dashboard.domain.reports.Report;
import com.dashboard.domain.reports.ReportProperties;
import com.dashboard.repository.GPSPointRepository;
import com.vividsolutions.jts.geom.Point;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class ReportService {
    static final Map<String, TemporalAdjuster> ADJUSTERS = new HashMap<>();
    private GPSPointRepository gpsPointRepository;

    @PostConstruct
    public void init() {
        ADJUSTERS.put("Days", TemporalAdjusters.ofDateAdjuster(d -> d));
        ADJUSTERS.put("Months", TemporalAdjusters.firstDayOfMonth());
        ADJUSTERS.put("Years", TemporalAdjusters.firstDayOfYear());
    }

    public Report getReport(ReportProperties reportProperties) {
        List<GPSPoint> gpsPointByVehicleIdAndTimestampBetweenOrderByTimestampAsc = gpsPointRepository.findGPSPointByVehicleIdAndTimestampBetweenOrderByTimestampAsc(
                reportProperties.getVehicleId(),
                reportProperties.getStartDate().with(LocalTime.MIN),
                reportProperties.getEndDate().with(LocalTime.MAX)
        );

        Map<LocalDate, List<GPSPoint>> pointsByDate = gpsPointByVehicleIdAndTimestampBetweenOrderByTimestampAsc.stream()
                .collect(Collectors.groupingBy(item -> item.getTimestamp().toLocalDate().with(ADJUSTERS.get(reportProperties.getChronoUnit()))));

        Map<LocalDate, Double> result = new HashMap<>();
        pointsByDate.forEach((key, value) -> {
            double path = 0;
            for (int i = 0; i < value.size() - 1; i++) {
                Point point1 = value.get(i).getPoint();
                Point point2 = value.get(i + 1).getPoint();
                path += calculateDistanceInMeters(
                        point1.getX(),
                        point1.getY(),
                        point2.getX(),
                        point2.getY()
                );
            }

            result.put(key, path);
        });

        return Report.builder()
                .type(reportProperties.getReportType())
                .period(reportProperties.getChronoUnit())
                .startDate(reportProperties.getStartDate())
                .endDate(reportProperties.getEndDate())
                .results(result)
                .build();
    }

    public double calculateDistanceInMeters(double lat1, double long1, double lat2, double long2) {
        return org.apache.lucene.util.SloppyMath.haversinMeters(lat1, long1, lat2, long2);
    }
}
