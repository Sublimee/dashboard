package com.dashboard.controller;

import com.dashboard.domain.reports.Report;
import com.dashboard.domain.reports.ReportProperties;
import com.dashboard.service.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/reports")
public class ReportsController {

    private final ReportService reportService;

    @PostMapping
    public Report getReport(@RequestBody ReportProperties reportProperties) {
        return reportService.getReport(reportProperties);
    }
}