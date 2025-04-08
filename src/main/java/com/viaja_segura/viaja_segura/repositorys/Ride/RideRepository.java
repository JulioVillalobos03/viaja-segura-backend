package com.viaja_segura.viaja_segura.repositorys.Ride;

import com.viaja_segura.viaja_segura.models.ride.Ride;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RideRepository extends JpaRepository<Ride, Long> {
    List<Ride> findByPassengerId(Long passengerId);
    List<Ride> findByDriverId(Long driverId);
    List<Ride> findByStatus_NameAndDriverIsNull(String statusName);

}
