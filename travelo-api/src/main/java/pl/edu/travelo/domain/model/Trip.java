package pl.edu.travelo.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import pl.edu.travelo.domain.enums.ReservationStatus;
import pl.edu.travelo.domain.validation.FieldValidator;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "trip")
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private static final String CURRENCY = "PLN";

    @Column(nullable = false)
    private LocalDateTime departureTime;

    @Column(nullable = false)
    private LocalDateTime arrivalTime;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private boolean isCancelled;


    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private City startCity;

    @ManyToOne
    @JoinColumn(name = "destination_id", nullable = false)
    private Destination destination;

    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @OneToMany(mappedBy = "trip")
    private final Set<Reservation> reservations = new HashSet<>();

    public Trip(LocalDateTime departureTime, LocalDateTime arrivalTime, double price,
                City startCity, Destination destination, Vehicle vehicle) {
        FieldValidator.validateDateTimeNotInThePast(departureTime, "Departure Time");
        FieldValidator.validateDateTimeNotInThePast(arrivalTime, "Arrival");
        FieldValidator.validateDateTimeRange(departureTime, arrivalTime);
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        setPrice(price);
        this.isCancelled = false;

        assignCity(startCity);
        assignDestination(destination);
        assignVehicle(vehicle);
    }

    public Trip(LocalDateTime departureTime, LocalDateTime arrivalTime, double price,
                City startCity, Destination destination, Vehicle vehicle, Set<Reservation> reservations) {
        this(departureTime, arrivalTime, price, startCity, destination, vehicle);

        FieldValidator.validateObjectNotNull(reservations, "Reservations list");
        for (Reservation reservation : reservations) {
            addReservation(reservation);
        }
    }

    protected Trip() {
    }

    public long getId() {
        return id;
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
        if (this.vehicle == null) return 0;

        int totalSeats = this.vehicle.getSeats().size();

        long bookedSeatsCount = this.reservations.stream()
                .filter(reservation -> reservation.getStatus() == ReservationStatus.PENDING
                        || reservation.getStatus() == ReservationStatus.COMPLETED)
                .flatMap(reservation -> reservation.getSeats().stream())
                .distinct()
                .count();

        int available = totalSeats - (int) bookedSeatsCount;

        return Math.max(0, available);
    }

    public boolean isFull() {
        return getAvailablePlaceCount() == 0;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }

    public String getCurrency() {
        return CURRENCY;
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
