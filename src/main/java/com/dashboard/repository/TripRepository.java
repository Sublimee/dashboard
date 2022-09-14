package com.dashboard.repository;

import com.dashboard.domain.Trip;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.*;

@Repository
public interface TripRepository extends PagingAndSortingRepository<Trip, Long> {

    List<Trip> findAllByVehicleIdAndStartDateBetweenAndEndDateBetween(Long vehicleId, ZonedDateTime from, ZonedDateTime to, ZonedDateTime from2, ZonedDateTime to2);

    Page<Trip> findAllByVehicleId(Long vehicleId, Pageable pageable);
}