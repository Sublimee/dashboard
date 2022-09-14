package com.dashboard.domain.reports;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportProperties {
    private Long enterpriseId;
    private Long vehicleId;
    private ReportType reportType;
    private String chronoUnit;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private ZonedDateTime startDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private ZonedDateTime endDate;

    public ReportProperties(Long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }
}