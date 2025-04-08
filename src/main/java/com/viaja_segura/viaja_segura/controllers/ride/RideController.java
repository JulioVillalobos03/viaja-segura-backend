package com.viaja_segura.viaja_segura.controllers.ride;

import com.viaja_segura.viaja_segura.dtos.ride.RideDto;
import com.viaja_segura.viaja_segura.models.driver.Driver;
import com.viaja_segura.viaja_segura.models.ride.Ride;
import com.viaja_segura.viaja_segura.models.ride_status.RideStatus;
import com.viaja_segura.viaja_segura.repositorys.Ride.RideRepository;
import com.viaja_segura.viaja_segura.repositorys.driver.DriverRepository;
import com.viaja_segura.viaja_segura.repositorys.ride_status.RideStatusRepository;
import com.viaja_segura.viaja_segura.services.ride.RideService;
import com.viaja_segura.viaja_segura.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/rides")
public class RideController {
    @Autowired
    private RideService rideService;
    @Autowired
    private RideStatusRepository rideStatusRepository;
    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private RideRepository rideRepository;

    @PostMapping("/create")
    public ResponseEntity<Ride> create(@RequestBody RideDto dto) {
        Ride createdRide = rideService.createRide(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRide);
    }

    @PutMapping("/{rideId}/assign-driver")
    public Ride assignDriver(Long rideId, Long driverId) {
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new RuntimeException("Viaje no encontrado"));

        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Conductor no encontrado"));

        if (ride.getDriver() != null) {
            throw new RuntimeException("Este viaje ya tiene un conductor asignado");
        }

        RideStatus acceptedStatus = rideStatusRepository.findByName("accepted")
                .orElseThrow(() -> new RuntimeException("Estado 'accepted' no encontrado"));

        ride.setDriver(driver);
        ride.setStatus(acceptedStatus);
        ride.setUpdatedAt(LocalDateTime.now());

        return rideRepository.save(ride);
    }


    @GetMapping("/pending")
    public ResponseEntity<List<Ride>> getPendingRides() {
        List<Ride> pendingRides = rideService.findPendingRides();
        return ResponseEntity.ok(pendingRides);
    }



    @GetMapping("/get-all")
    public ResponseEntity<Response<List<Ride>>> getAll() {
        return ResponseEntity.ok(rideService.findAll());
    }

    @GetMapping("/passenger/{id}")
    public ResponseEntity<List<Ride>> getByPassenger(@PathVariable Long id) {
        return ResponseEntity.ok(rideService.findByPassengerId(id));
    }

    @GetMapping("/driver/{id}")
    public ResponseEntity<List<Ride>> getByDriver(@PathVariable Long id) {
        return ResponseEntity.ok(rideService.findByDriverId(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ride> getById(@PathVariable Long id) {
        return ResponseEntity.ok(rideService.findById(id));
    }
}