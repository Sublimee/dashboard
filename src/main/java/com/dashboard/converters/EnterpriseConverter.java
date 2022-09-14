package com.dashboard.converters;

import com.dashboard.dto.EnterpriseDto;
import com.dashboard.domain.Driver;
import com.dashboard.domain.Enterprise;

import java.util.*;
import java.util.stream.Collectors;

public class EnterpriseConverter {
    public static EnterpriseDto toEnterpriseDto(Enterprise enterprise) {
        return EnterpriseDto.builder()
                .id(enterprise.getId())
                .name(enterprise.getName())
                .city(enterprise.getCity())
                .drivers(enterprise.getDrivers().stream()
                        .map(Driver::getId)
                        .collect(Collectors.toSet())
                )
                .timeZone(enterprise.getTimeZone())
                .build();
    }

    public static Enterprise toNewEnterprise(EnterpriseDto enterpriseDto) {
        return Enterprise.builder()
                .id(enterpriseDto.getId())
                .name(enterpriseDto.getName())
                .city(enterpriseDto.getCity())
                .timeZone(enterpriseDto.getTimeZone() == null ? TimeZone.getTimeZone("Africa/Casablanca") : enterpriseDto.getTimeZone())
                .build();
    }
}