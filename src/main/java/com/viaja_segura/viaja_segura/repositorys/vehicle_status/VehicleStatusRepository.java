package com.viaja_segura.viaja_segura.repositorys.vehicle_status;

import com.viaja_segura.viaja_segura.models.vehicle_status.VehicleStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleStatusRepository extends JpaRepository<VehicleStatus, Long> {
}
