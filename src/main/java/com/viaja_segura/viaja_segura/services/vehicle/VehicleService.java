package com.viaja_segura.viaja_segura.services.vehicle;

import com.viaja_segura.viaja_segura.dtos.vehicle.VehicleDto;
import com.viaja_segura.viaja_segura.models.driver.Driver;
import com.viaja_segura.viaja_segura.models.vehicle.Vehicle;
import com.viaja_segura.viaja_segura.repositorys.driver.DriverRepository;
import com.viaja_segura.viaja_segura.repositorys.vehicle.VehicleRepository;
import com.viaja_segura.viaja_segura.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VehicleService {
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private DriverRepository driverRepository;

    public Vehicle createVehicle(VehicleDto dto) {
        Driver driver = driverRepository.findById(dto.driverId)
                .orElseThrow(() -> new RuntimeException("Driver no encontrado"));

        Vehicle vehicle = new Vehicle();
        vehicle.setDriver(driver);
        vehicle.setPlateNumber(dto.plateNumber);
        vehicle.setBrand(dto.brand);
        vehicle.setModel(dto.model);
        vehicle.setColor(dto.color);
        vehicle.setSeats(dto.seats);
        vehicle.setYear(dto.year);
        vehicle.setCreatedAt(LocalDateTime.now());
        vehicle.setUpdatedAt(LocalDateTime.now());

        return vehicleRepository.save(vehicle);
    }

    public Response<List<Vehicle>> findAll() {
        List<Vehicle> data = vehicleRepository.findAll();
        return new Response<>(data, false, 200, "Vehículo registrado");
    }

    public Vehicle findByDriverId(Long driverId) {
        return vehicleRepository.findByDriverId(driverId);
    }
}
