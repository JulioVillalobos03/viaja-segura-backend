package com.viaja_segura.viaja_segura.models.driver;

import com.viaja_segura.viaja_segura.models.driver_personal_info.DriverPersonalInfo;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "drivers")
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String lastName;
    private LocalDate birthDate;
    private String sex;
    private String curp;
    private String municipality;
    private String city;

    @Column(unique = true, nullable = false)
    private String email;

    private String phone;

    @Column(nullable = false)
    private String password;

    private Boolean isAvailable;

    @Enumerated(EnumType.STRING)
    private DriverStatus status;

    private Double averageRating;

    @OneToOne(mappedBy = "driver", cascade = CascadeType.ALL)
    private DriverPersonalInfo personalInfo;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public enum DriverStatus {
        active, inactive, banned
    }
}
