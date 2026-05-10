package pl.edu.travelo.domain.model;

import pl.edu.travelo.validation.FieldValidator;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class Trip {
    private static final String CURRENCY = "PLN";
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private double price;
    private int availablePlaceCount;
    private boolean isFull;
    private boolean isCancelled;

    private City startCity;
    private Destination destination;
    private Vehicle vehicle;
    private final Set<Reservation> reservations = new HashSet<>();

    public Trip(LocalDateTime departureTime, LocalDateTime arrivalTime, double price, int availablePlaceCount,
                City startCity, Destination destination, Vehicle vehicle) {
        FieldValidator.validateDateTimeNotInThePast(departureTime, "Departure Time");
        FieldValidator.validateDateTimeNotInThePast(arrivalTime, "Arrival");
        FieldValidator.validateDateTimeRange(departureTime, arrivalTime);
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        setPrice(price);
        setAvailablePlaceCount(availablePlaceCount);
        this.isFull = false;
        this.isCancelled = false;

        assignCity(startCity);
        assignDestination(destination);
        assignVehicle(vehicle);
    }

    public Trip(LocalDateTime departureTime, LocalDateTime arrivalTime, double price, int availablePlaceCount,
                City startCity, Destination destination, Vehicle vehicle, Set<Reservation> reservations) {
        this(departureTime, arrivalTime, price, availablePlaceCount, startCity, destination, vehicle);

        FieldValidator.validateObjectNotNull(reservations, "Reservations list");
        for (Reservation reservation : reservations) {
            addReservation(reservation);
        }
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

    public void assignCity(City newCity) {
        FieldValidator.validateObjectNotNull(newCity, "City");

        if (this.startCity == newCity) return;

        if (this.startCity != null) {
            this.startCity.removeTrip(this);
        }

        this.startCity = newCity;
        this.startCity.addTrip(this);
    }

    public void assignDestination(Destination newDestination) {
        FieldValidator.validateObjectNotNull(newDestination, "Destination");

        if (this.destination == newDestination) return;

        if (this.destination != null) {
            this.destination.removeTrip(this);
        }

        this.destination = newDestination;
        this.destination.addTrip(this);
    }

    public void assignVehicle(Vehicle newVehicle) {
        FieldValidator.validateObjectNotNull(newVehicle, "Vehicle");

        if (this.vehicle == newVehicle) return;

        if (this.vehicle != null) {
            this.vehicle.removeTrip(this);
        }

        this.vehicle = newVehicle;
        this.vehicle.addTrip(this);
    }

    public Destination getDestination() {
        return destination;
    }

    public City getStartCity() {
        return startCity;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    void addReservation(Reservation reservation) {
        FieldValidator.validateObjectNotNull(reservation, "Reservation");
        this.reservations.add(reservation);
    }

    void removeReservation(Reservation reservation) {
        FieldValidator.validateObjectNotNull(reservation, "Reservation");

        if (!this.reservations.contains(reservation)) return;

        this.reservations.remove(reservation);
    }

    public Set<Reservation> getReservations() {
        return new HashSet<>(reservations);
    }
}
