package pl.edu.travelo.trip.model;

import pl.edu.travelo.validation.FieldValidator;

import java.time.LocalDateTime;

public class Trip {
    private static final String CURRENCY = "PLN";
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private double price;
    private int availablePlaceCount;
    private boolean isFull;
    private boolean isCancelled;

    public Trip(LocalDateTime departureTime, LocalDateTime arrivalTime, double price, int availablePlaceCount) {
        FieldValidator.validateDateTimeNotInThePast(departureTime, "Departure Time");
        FieldValidator.validateDateTimeNotInThePast(arrivalTime, "Arrival");
        FieldValidator.validateDateTimeRange(departureTime, arrivalTime);
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        setPrice(price);
        setAvailablePlaceCount(availablePlaceCount);
        this.isFull = false;
        this.isCancelled = false;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        FieldValidator.validateDateTimeNotInThePast(departureTime, "Departure Time");
        FieldValidator.validateDateTimeRange(departureTime, arrivalTime);
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        FieldValidator.validateDateTimeNotInThePast(arrivalTime, "Arrival");
        FieldValidator.validateDateTimeRange(arrivalTime, departureTime);
        this.arrivalTime = arrivalTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = FieldValidator.validatePositiveNumber(price, "Price");
    }

    public int getAvailablePlaceCount() {
        return availablePlaceCount;
    }

    public void setAvailablePlaceCount(int availablePlaceCount) {
        this.availablePlaceCount = FieldValidator.validatePositiveNumber(availablePlaceCount, "Available Places");
    }

    public boolean isFull() {
        return isFull;
    }

    public void setFull(boolean full) {
        isFull = full;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }
}
