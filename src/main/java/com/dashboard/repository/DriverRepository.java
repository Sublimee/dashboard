package com.dashboard.repository;

import com.dashboard.domain.Driver;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface DriverRepository extends PagingAndSortingRepository<Driver, Long> {

    List<Driver> findAllByIdIn(List<Long> ids);
}