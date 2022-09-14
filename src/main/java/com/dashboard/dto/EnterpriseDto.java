package com.dashboard.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EnterpriseDto {
    private Long id;
    private String name;
    private String city;
    private Set<Long> drivers;
    private Set<Long> vehicles;
    private Set<Long> managers;
    private TimeZone timeZone;
}