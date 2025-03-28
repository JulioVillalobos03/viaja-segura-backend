package com.viaja_segura.viaja_segura.models.driver_personal_info;

import com.viaja_segura.viaja_segura.models.driver.Driver;
import jakarta.persistence.*;

@Entity
@Table(name = "driver_personal_info")
public class DriverPersonalInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;

    private String licenseId;
    private Boolean testPassed;
}
