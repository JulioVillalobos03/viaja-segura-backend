package com.viaja_segura.viaja_segura.services.driver_location;

import com.viaja_segura.viaja_segura.dtos.location.DriverLocationDto;
import com.viaja_segura.viaja_segura.models.driver.Driver;
import com.viaja_segura.viaja_segura.models.driver_location.DriverLocation;
import com.viaja_segura.viaja_segura.repositorys.driver.DriverRepository;
import com.viaja_segura.viaja_segura.repositorys.driver_location.DriverLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DriverLocationService {
    @Autowired
    private DriverLocationRepository repo;
    @Autowired
    private DriverRepository driverRepo;

    public DriverLocation save(DriverLocationDto dto) {
        Driver driver = driverRepo.findById(dto.driverId).orElseThrow();
        DriverLocation location = new DriverLocation();
        location.setDriver(driver);
        location.setLatitude(dto.latitude);
        location.setLongitude(dto.longitude);
        location.setTimestamp(LocalDateTime.now());
        return repo.save(location);
    }

    public List<DriverLocation> getByDriver(Long driverId) {
        return repo.findByDriverId(driverId);
    }
}

