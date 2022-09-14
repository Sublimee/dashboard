package com.dashboard.repository;

import com.dashboard.domain.GPSPoint;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.*;

@Repository
public interface GPSPointRepository extends CrudRepository<GPSPoint, Long> {

    List<GPSPoint> findGPSPointByVehicleIdAndTimestampBetweenOrderByTimestampAsc(Long vehicleId, ZonedDateTime from, ZonedDateTime to);

    GPSPoint findFirstByVehicleIdAndTimestampAfterOrderByTimestampAsc(Long vehicleId, ZonedDateTime date);

    GPSPoint findFirstByVehicleIdAndTimestampBeforeOrderByTimestampDesc(Long vehicleId, ZonedDateTime date);
}