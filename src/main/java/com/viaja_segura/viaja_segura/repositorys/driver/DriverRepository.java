package com.viaja_segura.viaja_segura.repositorys.driver;

import com.viaja_segura.viaja_segura.models.driver.Driver;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DriverRepository extends JpaRepository<Driver, Long> {
    boolean existsByEmail(String email);

    Optional<Driver> findByEmail(String email);

    @EntityGraph(attributePaths = {"vehicle", "personalInfo"})
    List<Driver> findAll();

    Optional<Driver> findDriverById(Long id);

}
