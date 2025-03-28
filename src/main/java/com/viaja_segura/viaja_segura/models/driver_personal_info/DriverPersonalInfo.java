package com.viaja_segura.viaja_segura.models.driver_personal_info;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.viaja_segura.viaja_segura.models.driver.Driver;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "driver_personal_info")
public class DriverPersonalInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "driver_id")
    @JsonIgnore
    private Driver driver;

    private String licenseId;
    private Boolean testPassed;
    private LocalDate badgeExpiration;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public String getLicenseId() {
        return licenseId;
    }

    public void setLicenseId(String licenseId) {
        this.licenseId = licenseId;
    }

    public Boolean getTestPassed() {
        return testPassed;
    }

    public void setTestPassed(Boolean testPassed) {
        this.testPassed = testPassed;
    }

    public LocalDate getBadgeExpiration() {
        return badgeExpiration;
    }

    public void setBadgeExpiration(LocalDate badgeExpiration) {
        this.badgeExpiration = badgeExpiration;
    }
}
