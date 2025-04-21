package com.viaja_segura.viaja_segura.services.driver;

import com.viaja_segura.viaja_segura.dtos.driver.DriverDto;
import com.viaja_segura.viaja_segura.models.admin.Admin;
import com.viaja_segura.viaja_segura.models.driver.Driver;
import com.viaja_segura.viaja_segura.models.driver_personal_info.DriverPersonalInfo;
import com.viaja_segura.viaja_segura.models.driver_status.DriverStatus;
import com.viaja_segura.viaja_segura.repositorys.driver.DriverRepository;
import com.viaja_segura.viaja_segura.repositorys.driver.DriverStatusRepository;
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

    @Autowired
    private DriverStatusRepository driverStatusRepository;
    @Autowired
    private DriverRepository driverRepository;


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
        driver.setSite_address(dto.site_address);
        driver.setSite_name(dto.site_name);
        driver.setEmail(dto.email);
        driver.setPhone(dto.phone);
        driver.setPassword(passwordEncoder.encode(dto.password));
        driver.setAvailable(false);
        DriverStatus inactiveStatus = driverStatusRepository.findByName("inactive")
                .orElseThrow(() -> new RuntimeException("Estado 'inactive' no encontrado"));
        driver.setStatus(inactiveStatus);
        driver.setCreatedAt(LocalDateTime.now());
        driver.setUpdatedAt(LocalDateTime.now());

        DriverPersonalInfo info = new DriverPersonalInfo();
        info.setDriver(driver);
        info.setLicenseId(dto.licenseId);
        info.setTestPassed(dto.testPassed);
        info.setBadgeExpiration(LocalDate.parse(dto.badgeExpiration));
        driver.setPersonalInfo(info);

        Driver saved = repo.save(driver);


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

    @Transactional
    public Driver updateDriverInfo(Long id, DriverDto dto) {
        Driver driver = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Conductor no encontrado con ID: " + id));

        if (dto.name != null) driver.setName(dto.name);
        if (dto.lastName != null) driver.setLastName(dto.lastName);
        if (dto.birthDate != null) driver.setBirthDate(dto.birthDate);
        if (dto.sex != null) driver.setSex(dto.sex);
        if (dto.curp != null) driver.setCurp(dto.curp);
        if (dto.municipality != null) driver.setMunicipality(dto.municipality);
        if (dto.city != null) driver.setCity(dto.city);
        if (dto.site_address != null) driver.setSite_address(dto.site_address);
        if (dto.site_name != null) driver.setSite_name(dto.site_name);
        if (dto.phone != null) driver.setPhone(dto.phone);
        driver.setUpdatedAt(LocalDateTime.now());

        return repo.save(driver);
    }

    @Transactional
    public Driver updateDriverStatus(Long id, String statusName) {
        Driver driver = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Conductor no encontrado con ID: " + id));

        DriverStatus status = driverStatusRepository.findByName(statusName)
                .orElseThrow(() -> new RuntimeException("Estado no válido: " + statusName));

        driver.setStatus(status);
        driver.setUpdatedAt(LocalDateTime.now());

        return repo.save(driver);
    }


    public Driver updateAvailability(Long driverId, boolean available) {
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Conductor no encontrado"));

        driver.setAvailable(available);
        driver.setUpdatedAt(LocalDateTime.now());

        return driverRepository.save(driver);
    }
}
