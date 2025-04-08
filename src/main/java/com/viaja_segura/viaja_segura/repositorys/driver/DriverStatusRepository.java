package com.viaja_segura.viaja_segura.repositorys.driver;

import com.viaja_segura.viaja_segura.models.driver_status.DriverStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DriverStatusRepository extends JpaRepository<DriverStatus, Long> {
    Optional<DriverStatus> findByName(String name);
}
