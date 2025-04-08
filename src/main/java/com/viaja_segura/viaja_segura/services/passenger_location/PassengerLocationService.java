package com.viaja_segura.viaja_segura.services.passenger_location;

import com.viaja_segura.viaja_segura.dtos.location.PassengerLocationDto;
import com.viaja_segura.viaja_segura.models.passenger.Passenger;
import com.viaja_segura.viaja_segura.models.passenger_location.PassengerLocation;
import com.viaja_segura.viaja_segura.repositorys.passenger.PassengerRepository;
import com.viaja_segura.viaja_segura.repositorys.passenger_location.PassengerLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PassengerLocationService {
    @Autowired
    private PassengerLocationRepository repo;
    @Autowired
    private PassengerRepository passengerRepo;

    public PassengerLocation save(PassengerLocationDto dto) {
        Passenger passenger = passengerRepo.findById(dto.passengerId).orElseThrow();
        PassengerLocation location = new PassengerLocation();
        location.setPassenger(passenger);
        location.setLatitude(dto.latitude);
        location.setLongitude(dto.longitude);
        location.setTimestamp(LocalDateTime.now());
        return repo.save(location);
    }

    public List<PassengerLocation> getByPassenger(Long passengerId) {
        return repo.findByPassengerId(passengerId);
    }
}

