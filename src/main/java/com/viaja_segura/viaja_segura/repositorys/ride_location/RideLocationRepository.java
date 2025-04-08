package com.viaja_segura.viaja_segura.repositorys.ride_location;

import com.viaja_segura.viaja_segura.models.ride_location.RideLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RideLocationRepository extends JpaRepository<RideLocation, Long> {
    List<RideLocation> findByRideId(Long rideId);
}
