package com.viaja_segura.viaja_segura.repositorys.vehicle;

import com.viaja_segura.viaja_segura.models.vehicle.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    Vehicle findByDriverId(Long driverId);
}
