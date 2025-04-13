package com.viaja_segura.viaja_segura.controllers.users;

import com.viaja_segura.viaja_segura.dtos.admin.AdminDto;
import com.viaja_segura.viaja_segura.dtos.driver.DriverDto;
import com.viaja_segura.viaja_segura.dtos.passenger.PassengerDto;
import com.viaja_segura.viaja_segura.models.admin.Admin;
import com.viaja_segura.viaja_segura.models.driver.Driver;
import com.viaja_segura.viaja_segura.models.passenger.Passenger;
import com.viaja_segura.viaja_segura.repositorys.driver.DriverRepository;
import com.viaja_segura.viaja_segura.services.admin.AdminService;
import com.viaja_segura.viaja_segura.services.driver.DriverService;
import com.viaja_segura.viaja_segura.services.passenger.PassengerService;
import com.viaja_segura.viaja_segura.services.qr_driver.QrService;
import com.viaja_segura.viaja_segura.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private AdminService adminService;
    @Autowired private PassengerService passengerService;
    @Autowired private DriverService driverService;
    @Autowired private DriverRepository driverRepository;
    @Autowired
    private QrService qrService;

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

    @PutMapping("/drivers/{id}/info")
    public ResponseEntity<Driver> updateDriverInfo(
            @PathVariable Long id,
            @RequestBody DriverDto dto) {
        Driver updated = driverService.updateDriverInfo(id, dto);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/drivers/{id}/status")
    public ResponseEntity<Driver> updateDriverStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        Driver updated = driverService.updateDriverStatus(id, status);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/passengers/{id}/info")
    public ResponseEntity<Passenger> updateInfo(
            @PathVariable Long id,
            @RequestBody PassengerDto dto) {
        Passenger updated = passengerService.updatePassengerInfo(id, dto);
        return ResponseEntity.ok(updated);
    }


    @GetMapping("/drivers/available")
    public ResponseEntity<List<Driver>> getAvailableDrivers() {
        List<Driver> availableDrivers = driverRepository.findByIsAvailableTrue();
        return ResponseEntity.ok(availableDrivers);
    }

    @PutMapping("/drivers/{id}/available")
    public ResponseEntity<Driver> updateAvailability(
            @PathVariable Long id,
            @RequestParam boolean available) {
        Driver updated = driverService.updateAvailability(id, available);
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/drivers/{id}/generate-qr")
    public ResponseEntity<String> generateQr(@PathVariable Long id) {
        try {
            String base64 = qrService.generateAndSaveDriverQr(id);
            return ResponseEntity.ok(base64);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    @GetMapping(value = "/drivers/{id}/qr", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getQr(@PathVariable Long id) {
        try {
            String base64 = qrService.getDriverQr(id);
            byte[] imageBytes = Base64.getDecoder().decode(base64);
            return ResponseEntity.ok(imageBytes);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
