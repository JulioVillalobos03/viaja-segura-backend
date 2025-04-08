package com.viaja_segura.viaja_segura.repositorys.driver_location;

import com.viaja_segura.viaja_segura.models.driver_location.DriverLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DriverLocationRepository extends JpaRepository<DriverLocation, Long> {
    List<DriverLocation> findByDriverId(Long driverId);
}
