package com.viaja_segura.viaja_segura.models.ride_report;

import com.viaja_segura.viaja_segura.models.ride.Ride;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ride_reports")
public class RideReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ride_id")
    private Ride ride;

    private Long userId;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Column(columnDefinition = "TEXT")
    private String message;

    private LocalDateTime createdAt;

    public enum UserType {
        driver, passenger
    }
}
