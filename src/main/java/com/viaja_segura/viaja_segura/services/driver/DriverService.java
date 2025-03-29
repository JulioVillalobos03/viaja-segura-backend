package com.viaja_segura.viaja_segura.services.driver;

import com.viaja_segura.viaja_segura.dtos.driver.DriverDto;
import com.viaja_segura.viaja_segura.models.admin.Admin;
import com.viaja_segura.viaja_segura.models.driver.Driver;
import com.viaja_segura.viaja_segura.models.driver_personal_info.DriverPersonalInfo;
import com.viaja_segura.viaja_segura.repositorys.driver.DriverRepository;
import com.viaja_segura.viaja_segura.utils.EmailService;
import com.viaja_segura.viaja_segura.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class DriverService {
    @Autowired
    private DriverRepository repo;
    @Autowired private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    public Driver register(DriverDto dto) {
        if (repo.existsByEmail(dto.email)) {
            throw new RuntimeException("Email ya registrado");
        }

        Driver driver = new Driver();
        driver.setName(dto.name);
        driver.setLastName(dto.lastName);
        driver.setBirthDate(dto.birthDate);
        driver.setSex(dto.sex);
        driver.setCurp(dto.curp);
        driver.setMunicipality(dto.municipality);
        driver.setCity(dto.city);
        driver.setEmail(dto.email);
        driver.setPhone(dto.phone);
        driver.setPassword(passwordEncoder.encode(dto.password));
        driver.setAvailable(false);
        driver.setStatus(Driver.DriverStatus.inactive);
        driver.setCreatedAt(LocalDateTime.now());
        driver.setUpdatedAt(LocalDateTime.now());

        DriverPersonalInfo info = new DriverPersonalInfo();
        info.setDriver(driver);
        info.setLicenseId(dto.licenseId);
        info.setTestPassed(dto.testPassed);
        info.setBadgeExpiration(LocalDate.parse(dto.badgeExpiration));
        driver.setPersonalInfo(info);

        Driver saved = repo.save(driver);

        emailService.sendUserRegistrationEmail(
                saved.getEmail(),
                saved.getName(),
                saved.getLastName(),
                saved.getEmail(),
                "DRIVER"
        );

        return saved;
    }

    @Transactional(readOnly = true)
    public Response<List<Driver>> findAll() {
        List<Driver> data = repo.findAll();
        return new Response<>(data, false, 200, "Lista de conductores");
    }

    @Transactional(readOnly = true)
    public Driver findById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Conductor no encontrado con ID: " + id));
    }


}
