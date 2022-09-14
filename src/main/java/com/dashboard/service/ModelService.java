package com.dashboard.service;


import com.dashboard.converters.ModelConverter;
import com.dashboard.domain.Model;
import com.dashboard.dto.ModelDto;
import com.dashboard.repository.ModelRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@AllArgsConstructor
@Service
public class ModelService {

    private final ModelRepository modelRepository;

    public Iterable<ModelDto> findAll() {
        return StreamSupport.stream(modelRepository.findAll().spliterator(), false)
                .map(ModelConverter::toModelDto)
                .collect(Collectors.toList());
    }

    public Model findById(Long id) {
        return modelRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid model Id:" + id));
    }
}