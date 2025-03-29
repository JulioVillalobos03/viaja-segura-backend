package com.viaja_segura.viaja_segura.services.auth;

import com.viaja_segura.viaja_segura.models.admin.Admin;
import com.viaja_segura.viaja_segura.models.driver.Driver;
import com.viaja_segura.viaja_segura.models.passenger.Passenger;
import com.viaja_segura.viaja_segura.repositorys.admin.AdminRepository;
import com.viaja_segura.viaja_segura.repositorys.driver.DriverRepository;
import com.viaja_segura.viaja_segura.repositorys.passenger.PassengerRepository;
import com.viaja_segura.viaja_segura.utils.CodeInfo;
import com.viaja_segura.viaja_segura.utils.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class InMemoryPasswordResetService {

    private record CodeInfo(String code, Date expiresAt) {
        boolean isExpired() {
            return new Date().after(expiresAt);
        }
    }

    private final Map<String, CodeInfo> resetCodes = new ConcurrentHashMap<>();

    @Autowired private EmailService emailService;
    @Autowired private PassengerRepository passengerRepo;
    @Autowired private DriverRepository driverRepo;
    @Autowired private AdminRepository adminRepo;
    @Autowired private PasswordEncoder encoder;

    public void sendResetCode(String email) {
        Optional<Passenger> p = passengerRepo.findByEmail(email);
        Optional<Driver> d = driverRepo.findByEmail(email);
        Optional<Admin> a = adminRepo.findByEmail(email);

        if (p.isEmpty() && d.isEmpty() && a.isEmpty()) {
            throw new RuntimeException("No se encontró ningún usuario con ese correo");
        }

        String code = generateCode();

        // Calcular expiración (10 minutos después)
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, 10);
        Date expirationDate = calendar.getTime();

        resetCodes.put(email, new CodeInfo(code, expirationDate));

        emailService.sendResetCodeEmail(email, "Recuperación de contraseña - Viaja Segura", code);
    }


    public boolean validateCode(String email, String code) {
        CodeInfo info = resetCodes.get(email);
        return info != null && !info.isExpired() && info.code.equals(code);
    }

    public void updatePassword(String email, String code, String newPassword) {
        CodeInfo info = resetCodes.get(email);
        if (info == null || info.isExpired() || !info.code.equals(code)) {
            throw new RuntimeException("Código inválido o expirado");
        }

        // Buscar en passengers
        Optional<Passenger> passenger = passengerRepo.findByEmail(email);
        if (passenger.isPresent()) {
            passenger.get().setPassword(encoder.encode(newPassword));
            passengerRepo.save(passenger.get());
            resetCodes.remove(email);
            return;
        }

        // Buscar en drivers
        Optional<Driver> driver = driverRepo.findByEmail(email);
        if (driver.isPresent()) {
            driver.get().setPassword(encoder.encode(newPassword));
            driverRepo.save(driver.get());
            resetCodes.remove(email);
            return;
        }

        // Buscar en admins
        Optional<Admin> admin = adminRepo.findByEmail(email);
        if (admin.isPresent()) {
            admin.get().setPassword(encoder.encode(newPassword));
            adminRepo.save(admin.get());
            resetCodes.remove(email);
            return;
        }

        // Si no encontró el usuario
        throw new RuntimeException("Usuario no encontrado");
    }


    private String generateCode() {
        Random random = new Random();
        int number = 100000 + random.nextInt(900000); // Asegura 6 dígitos
        return String.valueOf(number);
    }
}
