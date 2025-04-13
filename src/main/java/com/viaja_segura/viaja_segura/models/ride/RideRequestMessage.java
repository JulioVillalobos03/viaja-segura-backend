package com.viaja_segura.viaja_segura.models.ride;

import java.math.BigDecimal;

public class RideRequestMessage {
    private Long rideId;
    private BigDecimal originLat;
    private BigDecimal originLng;
    private BigDecimal destinationLat;
    private BigDecimal destinationLng;
    private String originText;
    private String destinationText;

    public RideRequestMessage(
            Long rideId,
            BigDecimal originLat,
            BigDecimal originLng,
            BigDecimal destinationLat,
            BigDecimal destinationLng,
            String originText,
            String destinationText
    ) {
        this.rideId = rideId;
        this.originLat = originLat;
        this.originLng = originLng;
        this.destinationLat = destinationLat;
        this.destinationLng = destinationLng;
        this.originText = originText;
        this.destinationText = destinationText;
    }

    public Long getRideId() {
        return rideId;
    }

    public void setRideId(Long rideId) {
        this.rideId = rideId;
    }

    public BigDecimal getOriginLat() {
        return originLat;
    }

    public void setOriginLat(BigDecimal originLat) {
        this.originLat = originLat;
    }

    public BigDecimal getOriginLng() {
        return originLng;
    }

    public void setOriginLng(BigDecimal originLng) {
        this.originLng = originLng;
    }

    public BigDecimal getDestinationLat() {
        return destinationLat;
    }

    public void setDestinationLat(BigDecimal destinationLat) {
        this.destinationLat = destinationLat;
    }

    public BigDecimal getDestinationLng() {
        return destinationLng;
    }

    public void setDestinationLng(BigDecimal destinationLng) {
        this.destinationLng = destinationLng;
    }

    public String getOriginText() {
        return originText;
    }

    public void setOriginText(String originText) {
        this.originText = originText;
    }

    public String getDestinationText() {
        return destinationText;
    }

    public void setDestinationText(String destinationText) {
        this.destinationText = destinationText;
    }
}

