package com.dashboard.repository;

import com.dashboard.domain.Enterprise;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface EnterpriseRepository extends PagingAndSortingRepository<Enterprise, Long> {
    @EntityGraph(attributePaths = {"drivers"})
    Set<Enterprise> findByManagers_Id(Long managerId);
}