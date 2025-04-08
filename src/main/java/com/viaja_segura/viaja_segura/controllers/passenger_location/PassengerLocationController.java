package com.viaja_segura.viaja_segura.controllers.passenger_location;

import com.viaja_segura.viaja_segura.dtos.location.PassengerLocationDto;
import com.viaja_segura.viaja_segura.models.passenger_location.PassengerLocation;
import com.viaja_segura.viaja_segura.services.passenger_location.PassengerLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/location/passenger")
public class PassengerLocationController {
    @Autowired
    private PassengerLocationService service;

    @PostMapping
    public ResponseEntity<PassengerLocation> save(@RequestBody PassengerLocationDto dto) {
        return ResponseEntity.ok(service.save(dto));
    }

    @GetMapping("/{passengerId}")
    public ResponseEntity<List<PassengerLocation>> get(@PathVariable Long passengerId) {
        return ResponseEntity.ok(service.getByPassenger(passengerId));
    }
}

