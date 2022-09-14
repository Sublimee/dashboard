package com.dashboard.converters;

import com.dashboard.domain.Model;
import com.dashboard.dto.ModelDto;

public class ModelConverter {
    public static ModelDto toModelDto(Model model) {
        return ModelDto.builder()
                .id(model.getId())
                .brand(model.getBrand())
                .type(model.getType())
                .tankCapacityInLiters(model.getTankCapacityInLiters())
                .loadCapacityInTons(model.getLoadCapacityInTons())
                .seats(model.getSeats())
                .build();
    }
}