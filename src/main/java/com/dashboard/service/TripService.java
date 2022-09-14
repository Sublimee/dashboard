package com.dashboard.service;

import com.dashboard.converters.GPSPointConverter;
import com.dashboard.domain.Enterprise;
import com.dashboard.domain.GPSPoint;
import com.dashboard.domain.PageEntity;
import com.dashboard.domain.Trip;
import com.dashboard.domain.Vehicle;
import com.dashboard.dto.GPSPointDto;
import com.dashboard.dto.TripDto;
import com.dashboard.repository.GPSPointRepository;
import com.dashboard.repository.TripRepository;
import com.dashboard.repository.VehicleRepository;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static java.time.ZoneOffset.UTC;

@AllArgsConstructor
@Service
public class TripService {

    private GPSPointRepository repository;
    private VehicleRepository vehicleRepository;
    private TripRepository tripRepository;

    public List<GPSPointDto> getTrip(Long tripId) {
        Trip trip = tripRepository.findById(tripId).orElseThrow();
        ZoneId enterpriseZoneId = trip.getVehicle().getEnterprise().getTimeZone().toZoneId();

        List<GPSPoint> points = repository.findGPSPointByVehicleIdAndTimestampBetweenOrderByTimestampAsc(
                trip.getVehicle().getId(), trip.getStartDate(), trip.getEndDate());

        return points.stream()
                .map(point -> GPSPointConverter.toGPSPointDto(point, enterpriseZoneId, null))
                .collect(Collectors.toList());
    }

    public List<GPSPointDto> getMergedTrips(Long vehicleId, ZonedDateTime from, ZonedDateTime to) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow();

        ZonedDateTime utcFrom = from.withZoneSameInstant(UTC);
        ZonedDateTime utcTo = to.withZoneSameInstant(UTC);

        List<Trip> trips = tripRepository.findAllByVehicleIdAndStartDateBetweenAndEndDateBetween(vehicleId, utcFrom, utcTo, utcFrom, utcTo);

        return trips.stream()
                .map(trip -> repository.findGPSPointByVehicleIdAndTimestampBetweenOrderByTimestampAsc(vehicleId, trip.getStartDate(), trip.getEndDate()))
                .flatMap(Collection::stream)
                .map(point -> GPSPointConverter.toGPSPointDto(point, vehicle.getEnterprise().getTimeZone().toZoneId(), null))
                .collect(Collectors.toList());
    }

    public PageEntity<TripDto> getTrips(Long vehicleId, int page, int size) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow();
        Enterprise enterprise = vehicle.getEnterprise();
        ZoneId enterpriseZoneId = enterprise.getTimeZone().toZoneId();

        Page<Trip> tripsPage = tripRepository.findAllByVehicleId(
                vehicle.getId(),
                PageRequest.of(page, size)
        );

        List<TripDto> content = tripsPage.getContent().stream()
                .map(trip ->
                        {
                            GPSPoint first = repository.findFirstByVehicleIdAndTimestampAfterOrderByTimestampAsc(vehicleId, trip.getStartDate());
                            GPSPoint last = repository.findFirstByVehicleIdAndTimestampBeforeOrderByTimestampDesc(vehicleId, trip.getEndDate());
                            return TripDto.builder()
                                    .id(trip.getId())
                                    .startPoint(GPSPointConverter.toGPSPointDto(first, enterpriseZoneId, getGeocode(first)))
                                    .endPoint(GPSPointConverter.toGPSPointDto(last, enterpriseZoneId, getGeocode(last)))
                                    .build();
                        }
                )
                .collect(Collectors.toList());

        return new PageEntity<>(content, tripsPage.getTotalElements(), page, size);
    }

    @SneakyThrows
    public String getGeocode(GPSPoint point) {
//        double x = point.getPoint().getX();
//        double y = point.getPoint().getY();
//        OkHttpClient client = new OkHttpClient();
//        String encodedAddress = URLEncoder.encode(x + "," + y, StandardCharsets.UTF_8);
//        Request request = new Request.Builder()
//                .url("https://google-maps-geocoding.p.rapidapi.com/geocode/json?sensor=false&latlng=" + encodedAddress)
//                .get()
//                .addHeader("x-rapidapi-host", "google-maps-geocoding.p.rapidapi.com")
//                .addHeader("x-rapidapi-key", "b635c1a042msh42c1f3d59464a4ep140ee4jsn586d1a6e07a0")
//                .build();
//        ResponseBody responseBody = client.newCall(request).execute().body();
//        ObjectMapper objectMapper = new ObjectMapper();
//        GeocodeResult result = objectMapper.readValue(responseBody.string(), GeocodeResult.class);
//        return result.getResults().get(0).getFormattedAddress();
        return "description";
    }
}