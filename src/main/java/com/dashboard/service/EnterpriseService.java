package com.dashboard.service;


import com.dashboard.converters.EnterpriseConverter;
import com.dashboard.domain.Enterprise;
import com.dashboard.domain.Manager;
import com.dashboard.domain.User;
import com.dashboard.dto.EnterpriseDto;
import com.dashboard.repository.EnterpriseRepository;
import com.dashboard.repository.ManagerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class EnterpriseService {

    private final EnterpriseRepository enterpriseRepository;
    private final ManagerRepository managerRepository;

    public Iterable<EnterpriseDto> findAll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Manager manager = managerRepository.findById(((User) authentication.getPrincipal()).getId()).get();

        return manager.getEnterprises().stream()
                .map(EnterpriseConverter::toEnterpriseDto)
                .collect(Collectors.toList());
    }

    public Enterprise save(EnterpriseDto enterpriseDto) {
        Enterprise entity = EnterpriseConverter.toNewEnterprise(enterpriseDto);
        return enterpriseRepository.save(entity);
    }

    public Enterprise findById(Long id) {
        return enterpriseRepository.findById(id).get();
    }
}