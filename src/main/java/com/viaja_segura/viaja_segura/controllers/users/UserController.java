package com.viaja_segura.viaja_segura.controllers.users;

import com.viaja_segura.viaja_segura.dtos.admin.AdminDto;
import com.viaja_segura.viaja_segura.dtos.driver.DriverDto;
import com.viaja_segura.viaja_segura.dtos.passenger.PassengerDto;
import com.viaja_segura.viaja_segura.models.admin.Admin;
import com.viaja_segura.viaja_segura.models.driver.Driver;
import com.viaja_segura.viaja_segura.models.passenger.Passenger;
import com.viaja_segura.viaja_segura.services.admin.AdminService;
import com.viaja_segura.viaja_segura.services.driver.DriverService;
import com.viaja_segura.viaja_segura.services.passenger.PassengerService;
import com.viaja_segura.viaja_segura.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private AdminService adminService;
    @Autowired private PassengerService passengerService;
    @Autowired private DriverService driverService;

    @PostMapping("/register/admin")
    public ResponseEntity<Admin> registerAdmin(@RequestBody AdminDto dto) {
        return ResponseEntity.ok(adminService.register(dto));
    }

    @PostMapping("/register/passenger")
    public ResponseEntity<Passenger> registerPassenger(@RequestBody PassengerDto dto) {
        return ResponseEntity.ok(passengerService.register(dto));
    }

    @PostMapping("/register/driver")
    public ResponseEntity<Driver> registerDriver(@RequestBody DriverDto dto) {
        return ResponseEntity.ok(driverService.register(dto));
    }

    @GetMapping("/admins")
    public ResponseEntity<Response<List<Admin>>> getAdmins() {
        return ResponseEntity.ok(adminService.findAll());
    }

    @GetMapping("/passengers")
    public ResponseEntity<Response<List<Passenger>>> getPassengers() {
        return ResponseEntity.ok(passengerService.findAll());
    }

    @GetMapping("/drivers")
    public ResponseEntity<Response<List<Driver>>> getDrivers() {
        return ResponseEntity.ok(driverService.findAll());
    }

    @GetMapping("/admins/{id}")
    public ResponseEntity<Admin> getAdminById(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.findById(id));
    }

    @GetMapping("/passengers/{id}")
    public ResponseEntity<Passenger> getPassengerById(@PathVariable Long id) {
        return ResponseEntity.ok(passengerService.findById(id));
    }

    @GetMapping("/drivers/{id}")
    public ResponseEntity<Driver> getDriverById(@PathVariable Long id) {
        return ResponseEntity.ok(driverService.findById(id));
    }


}
