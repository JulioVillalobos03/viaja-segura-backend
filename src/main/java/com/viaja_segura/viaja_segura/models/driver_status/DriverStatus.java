package com.viaja_segura.viaja_segura.models.driver_status;

import jakarta.persistence.*;

@Entity
@Table(name = "driver_statuses")
public class DriverStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; // Ej: active, inactive, banned

    public DriverStatus() {
    }

    public DriverStatus(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
