package pl.edu.travelo.domain.model;

import pl.edu.travelo.validation.FieldValidator;

import java.util.HashSet;
import java.util.Set;

public class Seat {
    private int seatNumber;
    private int row;

    private Vehicle vehicle;
    private final Set<Reservation> reservations = new HashSet<>();

    public Seat(int seatNumber, int row, Vehicle vehicle) {
        FieldValidator.validateObjectNotNull(vehicle, "vehicle");
        setSeatNumber(seatNumber);
        setRow(row);
        vehicle.addSeat(this);
        this.vehicle = vehicle;
    }

    public Seat(int seatNumber, int row, Vehicle vehicle, Set<Reservation> reservations) {
        this(seatNumber, row, vehicle);

        FieldValidator.validateObjectNotNull(reservations, "reservations");
        for (Reservation reservation : reservations) {
            addReservation(reservation);
        }
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        FieldValidator.validatePositiveNumber(seatNumber, "Seat number");
        if (seatNumber > vehicle.getRowWidth()) throw new IllegalArgumentException("Seat number exceed row width");
        this.seatNumber = seatNumber;
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
}
