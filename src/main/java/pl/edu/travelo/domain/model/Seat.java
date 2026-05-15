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
import pl.edu.travelo.validation.FieldValidator;

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

    @Column(nullable = false)
    private boolean isBooked;

    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @ManyToMany(mappedBy = "seats")
    private final Set<Reservation> reservations = new HashSet<>();

    public Seat(int seatNumber, int row, Vehicle vehicle) {
        FieldValidator.validateObjectNotNull(vehicle, "vehicle");
        setSeatNumber(seatNumber);
        setRow(row);
        vehicle.addSeat(this);
        this.vehicle = vehicle;
        this.isBooked = false;
    }

    public Seat(int seatNumber, int row, Vehicle vehicle, Set<Reservation> reservations) {
        this(seatNumber, row, vehicle);

        FieldValidator.validateObjectNotNull(reservations, "reservations");
        for (Reservation reservation : reservations) {
            addReservation(reservation);
        }
    }

    public Seat() {
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        FieldValidator.validatePositiveNumber(seatNumber, "Seat number");
        if (seatNumber > vehicle.getRowWidth()) throw new IllegalArgumentException("Seat number exceed row width");
        this.seatNumber = seatNumber;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
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
            case BUS -> 20.0;
            case PLAIN -> 150.0;
            case TRAIN -> 300.0;
        };
    }
}
