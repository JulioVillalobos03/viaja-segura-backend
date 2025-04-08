package com.viaja_segura.viaja_segura.controllers.ride_location;

import com.viaja_segura.viaja_segura.dtos.location.RideLocationDto;
import com.viaja_segura.viaja_segura.models.ride_location.RideLocation;
import com.viaja_segura.viaja_segura.services.ride_location.RideLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/location/ride")
public class RideLocationController {
    @Autowired
    private RideLocationService service;

    @PostMapping
    public ResponseEntity<RideLocation> save(@RequestBody RideLocationDto dto) {
        return ResponseEntity.ok(service.save(dto));
    }

    @GetMapping("/{rideId}")
    public ResponseEntity<List<RideLocation>> get(@PathVariable Long rideId) {
        return ResponseEntity.ok(service.getByRide(rideId));
    }
}

