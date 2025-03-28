package com.viaja_segura.viaja_segura.controllers.auth;

import com.viaja_segura.viaja_segura.dtos.auth.LoginDto;
import com.viaja_segura.viaja_segura.dtos.auth.LoginResponseDto;
import com.viaja_segura.viaja_segura.models.admin.Admin;
import com.viaja_segura.viaja_segura.models.driver.Driver;
import com.viaja_segura.viaja_segura.models.passenger.Passenger;
import com.viaja_segura.viaja_segura.repositorys.admin.AdminRepository;
import com.viaja_segura.viaja_segura.repositorys.driver.DriverRepository;
import com.viaja_segura.viaja_segura.repositorys.passenger.PassengerRepository;
import com.viaja_segura.viaja_segura.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AdminRepository adminRepo;
    @Autowired private PassengerRepository passengerRepo;
    @Autowired private DriverRepository driverRepo;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto req) {
        // Verifica en cada tabla
        Optional<Admin> adminOpt = adminRepo.findByEmail(req.email);
        if (adminOpt.isPresent() && passwordEncoder.matches(req.password, adminOpt.get().getPassword())) {
            String token = jwtService.generateToken(adminOpt.get().getEmail(), "ADMIN");
            return ResponseEntity.ok(new LoginResponseDto(token));
        }

        Optional<Passenger> passengerOpt = passengerRepo.findByEmail(req.email);
        if (passengerOpt.isPresent() && passwordEncoder.matches(req.password, passengerOpt.get().getPassword())) {
            String token = jwtService.generateToken(passengerOpt.get().getEmail(), "PASSENGER");
            return ResponseEntity.ok(new LoginResponseDto(token));
        }

        Optional<Driver> driverOpt = driverRepo.findByEmail(req.email);
        if (driverOpt.isPresent() && passwordEncoder.matches(req.password, driverOpt.get().getPassword())) {
            String token = jwtService.generateToken(driverOpt.get().getEmail(), "DRIVER");
            return ResponseEntity.ok(new LoginResponseDto(token));
        }

        return ResponseEntity.status(401).body("Credenciales inválidas");
    }
}
