package com.viaja_segura.viaja_segura.services.location;

import com.viaja_segura.viaja_segura.dtos.websocket.LocationUpdateDto;
import com.viaja_segura.viaja_segura.models.driver.Driver;
import com.viaja_segura.viaja_segura.models.driver_location.DriverLocation;
import com.viaja_segura.viaja_segura.models.passenger.Passenger;
import com.viaja_segura.viaja_segura.models.passenger_location.PassengerLocation;
import com.viaja_segura.viaja_segura.repositorys.driver.DriverRepository;
import com.viaja_segura.viaja_segura.repositorys.driver_location.DriverLocationRepository;
import com.viaja_segura.viaja_segura.repositorys.passenger.PassengerRepository;
import com.viaja_segura.viaja_segura.repositorys.passenger_location.PassengerLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LocationService {
    @Autowired
    private PassengerRepository passengerRepo;
    @Autowired private DriverRepository driverRepo;
    @Autowired private PassengerLocationRepository passengerLocationRepo;
    @Autowired private DriverLocationRepository driverLocationRepo;

    public void updateLocation(LocationUpdateDto dto) {
        BigDecimal lat = BigDecimal.valueOf(dto.getLat());
        BigDecimal lng = BigDecimal.valueOf(dto.getLng());

        if ("PASSENGER".equalsIgnoreCase(dto.getRole())) {
            Passenger passenger = passengerRepo.findById(dto.getUserId()).orElseThrow();
            List<PassengerLocation> locations = passengerLocationRepo.findByPassengerId(passenger.getId());
            PassengerLocation location;

            if (locations.isEmpty()) {
                location = new PassengerLocation();
                location.setPassenger(passenger);
            } else {
                location = locations.get(0); // Usamos el primer registro
            }

            location.setPassenger(passenger);
            location.setLatitude(lat);
            location.setLongitude(lng);
            location.setUpdatedAt(LocalDateTime.now());
            passengerLocationRepo.save(location);

        } else if ("DRIVER".equalsIgnoreCase(dto.getRole())) {
            Driver driver = driverRepo.findById(dto.getUserId()).orElseThrow();
            List<DriverLocation> locations = driverLocationRepo.findByDriverId(driver.getId());
            DriverLocation location;

            if (locations.isEmpty()) {
                location = new DriverLocation();
                location.setDriver(driver);
            } else {
                location = locations.get(0);
            }

            location.setDriver(driver);
            location.setLatitude(lat);
            location.setLongitude(lng);
            location.setUpdatedAt(LocalDateTime.now());
            driverLocationRepo.save(location);
        }
    }
}
