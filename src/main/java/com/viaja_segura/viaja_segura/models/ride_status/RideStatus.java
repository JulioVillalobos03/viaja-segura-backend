package com.viaja_segura.viaja_segura.models.ride_status;

import jakarta.persistence.*;

@Entity
@Table(name = "ride_statuses")
public class RideStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name; // pending, accepted, in_progress, etc.

    public RideStatus() {}

    public RideStatus(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
