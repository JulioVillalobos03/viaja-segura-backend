package com.viaja_segura.viaja_segura.models.vehicle;

import com.viaja_segura.viaja_segura.models.driver.Driver;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "vehicles")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;

    private String plateNumber;
    private String brand;
    private String model;
    private String color;
    private Integer seats;
    private Integer year;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
