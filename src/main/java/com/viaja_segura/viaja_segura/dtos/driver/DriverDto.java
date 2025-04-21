package com.viaja_segura.viaja_segura.dtos.driver;

import com.viaja_segura.viaja_segura.dtos.vehicle.VehicleDto;

import java.time.LocalDate;

public class DriverDto {
    public Long id;
    public String name;
    public String lastName;
    public LocalDate birthDate;
    public String sex;
    public String curp;
    public String municipality;
    public String city;
    public String site_address;
    public String site_name;
    public String email;
    public String password;
    public String phone;
    public Double averageRating;
    public Boolean isAvailable;
    public String licenseId;
    public Boolean testPassed;
    public String badgeExpiration;
    public VehicleDto vehicle;
}
