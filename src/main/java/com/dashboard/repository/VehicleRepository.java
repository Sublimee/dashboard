package com.dashboard.repository;

import com.dashboard.domain.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface VehicleRepository extends PagingAndSortingRepository<Vehicle, Long> {

    Page<Vehicle> findAllByEnterpriseIdIn(Set<Long> enterpriseIds, Pageable pageable);
}