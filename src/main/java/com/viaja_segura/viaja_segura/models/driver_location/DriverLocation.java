package com.viaja_segura.viaja_segura.models.driver_location;

import com.viaja_segura.viaja_segura.models.driver.Driver;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "driver_locations")
public class DriverLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;

    private BigDecimal latitude;
    private BigDecimal longitude;
    private LocalDateTime updatedAt;
}
