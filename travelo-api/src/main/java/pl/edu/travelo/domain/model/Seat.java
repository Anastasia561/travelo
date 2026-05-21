package pl.edu.travelo.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import pl.edu.travelo.domain.enums.ReservationStatus;
import pl.edu.travelo.domain.validation.FieldValidator;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "seat")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private int seatNumber;

    @Column(nullable = false)
    private int row;

    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @ManyToMany(mappedBy = "seats")
    private final Set<Reservation> reservations = new HashSet<>();

    Seat(int seatNumber, int row, Vehicle vehicle) {
        FieldValidator.validateObjectNotNull(vehicle, "vehicle");
        setSeatNumber(seatNumber);
        setRow(row);
        this.vehicle = vehicle;
    }

    protected Seat() {
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        FieldValidator.validatePositiveNumber(seatNumber, "Seat number");
        if (seatNumber > vehicle.getRowWidth()) throw new IllegalArgumentException("Seat number exceed row width");
        this.seatNumber = seatNumber;
    }

    public long getId() {
        return id;
    }

    public boolean isBooked(long tripId) {
        return this.reservations.stream()
                .anyMatch(reservation ->
                        reservation.getTrip() != null
                                && reservation.getTrip().getId() == tripId
                                && (reservation.getStatus() == ReservationStatus.COMPLETED
                                || reservation.getStatus() == ReservationStatus.PENDING)
                );
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        FieldValidator.validatePositiveNumber(row, "Row");
        if (row > vehicle.getMaxRow()) throw new IllegalArgumentException("Row exceed max row number");
        this.row = row;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void addReservation(Reservation reservation) {
        FieldValidator.validateObjectNotNull(reservation, "Reservation");
        this.reservations.add(reservation);
        reservation.addSeat(this);
    }

    public void removeReservation(Reservation reservation) {
        FieldValidator.validateObjectNotNull(reservation, "Reservation");

        if (!this.reservations.contains(reservation)) return;

        this.reservations.remove(reservation);
        reservation.removeSeat(this);
    }

    public Set<Reservation> getReservations() {
        return new HashSet<>(reservations);
    }

    public double getPrice() {
        return switch (vehicle.getVehicleType()) {
            case COACH_BUS -> 70.0;
            case SHUTTLE_BUS -> 40.0;
            case TRANSIT_BUS -> 100.0;
        };
    }
}
