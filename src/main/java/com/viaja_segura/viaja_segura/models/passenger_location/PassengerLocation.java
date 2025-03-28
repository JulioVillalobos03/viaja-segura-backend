package com.viaja_segura.viaja_segura.models.passenger_location;

import com.viaja_segura.viaja_segura.models.passenger.Passenger;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "passenger_locations")
public class PassengerLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "passenger_id")
    private Passenger passenger;

    private BigDecimal latitude;
    private BigDecimal longitude;
    private LocalDateTime updatedAt;
}
