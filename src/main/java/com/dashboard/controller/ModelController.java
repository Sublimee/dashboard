package com.dashboard.controller;

import com.dashboard.domain.Model;
import com.dashboard.repository.ModelRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/models")
public class ModelController {

    private final ModelRepository modelRepository;

    @GetMapping
    public Iterable<Model> getModels() {
        return modelRepository.findAll();
    }
}