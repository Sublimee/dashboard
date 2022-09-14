package com.dashboard.controller;

import com.dashboard.dto.EnterpriseDto;
import com.dashboard.domain.Enterprise;
import com.dashboard.service.EnterpriseService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/enterprises")
public class EnterpriseController {

    private final EnterpriseService enterpriseService;

    @GetMapping
    Iterable<EnterpriseDto> getEnterprises() {
        return enterpriseService.findAll();
    }

    @PostMapping
    Enterprise saveEnterprise(@RequestBody EnterpriseDto enterpriseDto) {
        return enterpriseService.save(enterpriseDto);
    }
}