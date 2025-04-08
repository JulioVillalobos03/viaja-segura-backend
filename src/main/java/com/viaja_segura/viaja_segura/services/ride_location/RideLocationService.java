package com.viaja_segura.viaja_segura.services.ride_location;

import com.viaja_segura.viaja_segura.dtos.location.RideLocationDto;
import com.viaja_segura.viaja_segura.models.ride.Ride;
import com.viaja_segura.viaja_segura.models.ride_location.RideLocation;
import com.viaja_segura.viaja_segura.repositorys.Ride.RideRepository;
import com.viaja_segura.viaja_segura.repositorys.ride_location.RideLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RideLocationService {
    @Autowired
    private RideLocationRepository repo;
    @Autowired
    private RideRepository rideRepo;

    public RideLocation save(RideLocationDto dto) {
        Ride ride = rideRepo.findById(dto.rideId).orElseThrow();
        RideLocation location = new RideLocation();
        location.setRide(ride);
        location.setLatitude(dto.latitude);
        location.setLongitude(dto.longitude);
        location.setTimestamp(LocalDateTime.now());
        return repo.save(location);
    }

    public List<RideLocation> getByRide(Long rideId) {
        return repo.findByRideId(rideId);
    }
}

