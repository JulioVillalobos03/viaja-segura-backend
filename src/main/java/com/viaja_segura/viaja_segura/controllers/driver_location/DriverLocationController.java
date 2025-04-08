package com.viaja_segura.viaja_segura.controllers.driver_location;

import com.viaja_segura.viaja_segura.dtos.location.DriverLocationDto;
import com.viaja_segura.viaja_segura.models.driver_location.DriverLocation;
import com.viaja_segura.viaja_segura.services.driver_location.DriverLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/location/driver")
public class DriverLocationController {
    @Autowired
    private DriverLocationService service;

    @PostMapping
    public ResponseEntity<DriverLocation> save(@RequestBody DriverLocationDto dto) {
        return ResponseEntity.ok(service.save(dto));
    }

    @GetMapping("/{driverId}")
    public ResponseEntity<List<DriverLocation>> get(@PathVariable Long driverId) {
        return ResponseEntity.ok(service.getByDriver(driverId));
    }
}

