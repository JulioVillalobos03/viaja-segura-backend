package com.viaja_segura.viaja_segura.services.ride;

import com.viaja_segura.viaja_segura.dtos.ride.RideDto;
import com.viaja_segura.viaja_segura.models.driver.Driver;
import com.viaja_segura.viaja_segura.models.passenger.Passenger;
import com.viaja_segura.viaja_segura.models.ride.Ride;
import com.viaja_segura.viaja_segura.models.ride_status.RideStatus;
import com.viaja_segura.viaja_segura.repositorys.Ride.RideRepository;
import com.viaja_segura.viaja_segura.repositorys.driver.DriverRepository;
import com.viaja_segura.viaja_segura.repositorys.passenger.PassengerRepository;
import com.viaja_segura.viaja_segura.repositorys.ride_status.RideStatusRepository;
import com.viaja_segura.viaja_segura.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RideService {
    @Autowired
    private RideRepository rideRepository;
    @Autowired
    private PassengerRepository passengerRepository;
    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private RideStatusRepository rideStatusRepository;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public Ride createRide(RideDto dto) {
        Passenger passenger = passengerRepository.findById(dto.getPassengerId())
                .orElseThrow(() -> new RuntimeException("Pasajero no encontrado"));

        RideStatus pendingStatus = rideStatusRepository.findByName("pending")
                .orElseThrow(() -> new RuntimeException("Status 'pending' no encontrado"));

        Ride ride = new Ride();
        ride.setPassenger(passenger);
        ride.setOriginLat(dto.getOriginLat());
        ride.setOriginLng(dto.getOriginLng());
        ride.setDestinationLat(dto.getDestinationLat());
        ride.setDestinationLng(dto.getDestinationLng());
        ride.setPrice(dto.getPrice());
        ride.setDistanceKm(dto.getDistanceKm());
        ride.setStatus(pendingStatus);
        ride.setCreatedAt(LocalDateTime.now());
        ride.setUpdatedAt(LocalDateTime.now());

        Ride savedRide = rideRepository.save(ride);

        // Notificar a los conductores
        messagingTemplate.convertAndSend("/topic/rides/new", savedRide);

        return savedRide;
    }

    public Response<List<Ride>> findAll() {
        List<Ride> rides = rideRepository.findAll();
        return new Response<>(rides, false, 200, "Lista de viajes");
    }

    public Ride findById(Long id) {
        return rideRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Viaje no encontrado"));
    }

    public List<Ride> findByPassengerId(Long passengerId) {
        return rideRepository.findByPassengerId(passengerId);
    }

    public List<Ride> findByDriverId(Long driverId) {
        return rideRepository.findByDriverId(driverId);
    }

    public Ride assignDriver(Long rideId, Long driverId) {
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new RuntimeException("Viaje no encontrado"));

        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Conductor no encontrado"));

        if (ride.getDriver() != null) {
            throw new RuntimeException("Este viaje ya tiene un conductor asignado");
        }

        RideStatus acceptedStatus = rideStatusRepository.findByName("accepted")
                .orElseThrow(() -> new RuntimeException("Estado 'accepted' no encontrado"));

        ride.setDriver(driver);
        ride.setStatus(acceptedStatus);
        ride.setUpdatedAt(LocalDateTime.now());

        return rideRepository.save(ride);
    }

    public List<Ride> findPendingRides() {
        return rideRepository.findByStatus_NameAndDriverIsNull("pending");
    }


}
