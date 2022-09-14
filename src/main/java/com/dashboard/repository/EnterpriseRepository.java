package com.dashboard.repository;

import com.dashboard.domain.Enterprise;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnterpriseRepository extends PagingAndSortingRepository<Enterprise, Long> {
}