package com.dashboard.repository;

import com.dashboard.domain.Model;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModelRepository extends PagingAndSortingRepository<Model, Long> {
}