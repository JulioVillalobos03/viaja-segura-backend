package com.viaja_segura.viaja_segura.repositorys.passenger_location;

import com.viaja_segura.viaja_segura.models.passenger_location.PassengerLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PassengerLocationRepository extends JpaRepository<PassengerLocation, Long> {
    List<PassengerLocation> findByPassengerId(Long passengerId);
}
