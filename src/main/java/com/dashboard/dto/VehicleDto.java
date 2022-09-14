package com.dashboard.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.dashboard.domain.Brand;
import com.dashboard.domain.Color;
import com.dashboard.domain.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VehicleDto {
    private Long id;
    private Long price;
    private Color color;

    private Long modelId;
    private Brand brand;
    private Type type;
    private Double tankCapacityInLiters;
    private Double loadCapacityInTons;
    private int seats;

    private Long enterpriseId;
    private TimeZone enterpriseTimeZone;

    private Set<Long> drivers;
    private ZonedDateTime purchaseDateTime;
}