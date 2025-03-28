package com.viaja_segura.viaja_segura.models.ride_location;

import com.viaja_segura.viaja_segura.models.ride.Ride;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ride_locations")
public class RideLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ride_id")
    private Ride ride;

    private BigDecimal latitude;
    private BigDecimal longitude;
    private LocalDateTime timestamp;
}
