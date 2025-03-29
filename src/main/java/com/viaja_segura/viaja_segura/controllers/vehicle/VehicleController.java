package com.viaja_segura.viaja_segura.controllers.vehicle;

import com.viaja_segura.viaja_segura.dtos.vehicle.VehicleDto;
import com.viaja_segura.viaja_segura.models.vehicle.Vehicle;
import com.viaja_segura.viaja_segura.services.vehicle.VehicleService;
import com.viaja_segura.viaja_segura.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {
    @Autowired
    private VehicleService vehicleService;

    @PostMapping("/register")
    public ResponseEntity<Vehicle> create(@RequestBody VehicleDto dto) {
        return ResponseEntity.ok(vehicleService.createVehicle(dto));
    }

    @GetMapping("/get-all")
    public ResponseEntity<Response<List<Vehicle>>> getAll() {
        return ResponseEntity.ok(vehicleService.findAll());
    }

    @GetMapping("/driver/{driverId}")
    public ResponseEntity<Vehicle> getByDriverId(@PathVariable Long driverId) {
        return ResponseEntity.ok(vehicleService.findByDriverId(driverId));
    }
}
