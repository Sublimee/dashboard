package com.dashboard.controller;

import com.dashboard.converters.EnterpriseConverter;
import com.dashboard.converters.VehicleConverter;
import com.dashboard.domain.Color;
import com.dashboard.domain.Vehicle;
import com.dashboard.domain.reports.Report;
import com.dashboard.domain.reports.ReportProperties;
import com.dashboard.domain.reports.ReportType;
import com.dashboard.dto.VehicleDto;
import com.dashboard.service.EnterpriseService;
import com.dashboard.service.ModelService;
import com.dashboard.service.ReportService;
import com.dashboard.service.VehicleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class ViewController {

    private final ModelService modelService;
    private final VehicleService vehicleService;
    private final EnterpriseService enterpriseService;
    private final ReportService reportService;

    @GetMapping
    public String home() {
        return "home";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @GetMapping("/vehicles")
    public String vehicles(@RequestParam Long enterpriseId, @RequestParam Long page, @RequestParam Long size, Model model) {
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("enterpriseId", enterpriseId);

        return "vehicles";
    }

    @GetMapping("/form/reports/create")
    public String vehicles(@RequestParam Long enterpriseId, Model model) {
        model.addAttribute("reportProperties", new ReportProperties(enterpriseId));
        model.addAttribute("enterpriseId", enterpriseId);
        model.addAttribute("vehicles", enterpriseService.findById(enterpriseId).getVehicles().stream().map(Vehicle::getId).collect(Collectors.toList()));
        model.addAttribute("reportTypeList", ReportType.values());
        model.addAttribute("chronoUnitList", List.of(ChronoUnit.DAYS, ChronoUnit.MONTHS, ChronoUnit.YEARS));

        return "form/reports/create";
    }

    @GetMapping("/vehicle")
    public String vehicle(@RequestParam Long vehicleId, @RequestParam Long page, @RequestParam Long size, Model model) {
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("vehicleId", vehicleId);

        return "vehicle";
    }

    @GetMapping("form/vehicles/update")
    public String updateVehicle(@RequestParam Long enterpriseId, @RequestParam Long vehicleId, Model model) {
        VehicleDto vehicleDto = VehicleConverter.toVehicleDto(vehicleService.findById(vehicleId));

        fillUpdateVehicleModel(enterpriseId, model, vehicleDto);

        return "form/vehicles/create";
    }

    @GetMapping("form/vehicles/create")
    public String createVehicle(@RequestParam Long enterpriseId, Model model) {
        VehicleDto vehicleDto = new VehicleDto();
        vehicleDto.setEnterpriseId(enterpriseId);

        fillUpdateVehicleModel(enterpriseId, model, vehicleDto);

        return "form/vehicles/create";
    }

    @PostMapping("form/vehicles-create")
    public String createVehicleAndBack(VehicleDto vehicleDto) {
        vehicleService.update(vehicleDto);

        return "redirect:/vehicles?enterpriseId=" + vehicleDto.getEnterpriseId() + "&page=0&size=5";
    }

    @PostMapping("form/report-generate")
    public String generateReport(ReportProperties reportProperties, RedirectAttributes redirectAttrs) {
        Report report = reportService.getReport(reportProperties);
        redirectAttrs.addFlashAttribute("keys", report.getResults().keySet().stream().map(x -> x.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))).collect(Collectors.toList()));
        redirectAttrs.addFlashAttribute("values", report.getResults().values());
        return "redirect:/report";
    }

    @GetMapping("report")
    public String report(Model model) {
        return "report";
    }

    private void fillUpdateVehicleModel(Long enterpriseId, Model model, VehicleDto vehicleDto) {
        model.addAttribute("vehicle", vehicleDto);
        model.addAttribute("colors", Color.values());
        model.addAttribute("models", modelService.findAll());
        model.addAttribute("enterpriseId", enterpriseId);
        model.addAttribute("drivers", EnterpriseConverter.toEnterpriseDto(enterpriseService.findById(enterpriseId)).getDrivers());
    }
}
