package com.viaja_segura.viaja_segura.models.ride_rating;

import com.viaja_segura.viaja_segura.models.driver.Driver;
import com.viaja_segura.viaja_segura.models.passenger.Passenger;
import com.viaja_segura.viaja_segura.models.ride.Ride;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ride_ratings")
public class RideRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "ride_id", unique = true)
    private Ride ride;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;

    @ManyToOne
    @JoinColumn(name = "passenger_id")
    private Passenger passenger;

    private Integer rating;
    private Integer passengerRating;

    @Column(columnDefinition = "TEXT")
    private String comment;

    private LocalDateTime createdAt;
}
