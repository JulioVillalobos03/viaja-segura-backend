package com.viaja_segura.viaja_segura.repositorys.ride_status;

import com.viaja_segura.viaja_segura.models.ride_status.RideStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RideStatusRepository extends JpaRepository<RideStatus, Long> {
    Optional<RideStatus> findByName(String name);
}
