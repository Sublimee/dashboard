package com.dashboard.domain.reports;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Report {
    private ReportType type;
    private String period;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private ZonedDateTime startDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private ZonedDateTime endDate;
    private Map<LocalDate, Double> results;

    private void setType(ReportType type) {
        this.type = type;
    }
}