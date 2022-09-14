package com.dashboard.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.dashboard.domain.Brand;
import com.dashboard.domain.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ModelDto {
    private Long id;
    private Brand brand;
    private Type type;
    private Double tankCapacityInLiters;
    private Double loadCapacityInTons;
    private int seats;
}