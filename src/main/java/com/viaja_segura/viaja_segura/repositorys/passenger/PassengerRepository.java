package com.viaja_segura.viaja_segura.repositorys.passenger;

import com.viaja_segura.viaja_segura.models.passenger.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {
    boolean existsByEmail(String email);
    Optional<Passenger> findByEmail(String email);
    Optional<Passenger> findPassengerById(Long id);
}
