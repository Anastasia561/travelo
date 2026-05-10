package pl.edu.travelo.domain.model;

import pl.edu.travelo.validation.FieldValidator;
import pl.edu.travelo.domain.enums.VehicleType;

import java.util.HashSet;
import java.util.Set;

public class Vehicle {
    private String vehicleNumber;
    private VehicleType vehicleType;
    private int maxRow;
    private int rowWidth;

    private final Set<Seat> seats = new HashSet<>();
    private final Set<Trip> trips = new HashSet<>();
    private final Set<Shift> shifts = new HashSet<>();

    public Vehicle(String vehicleNumber, VehicleType vehicleType, int maxRow, int rowWidth) {
        setVehicleNumber(vehicleNumber);
        setVehicleType(vehicleType);
        setMaxRow(maxRow);
        setRowWidth(rowWidth);
    }

    public Vehicle(String vehicleNumber, VehicleType vehicleType, int maxRow, int rowWidth, Set<Trip> trips,
                   Set<Shift> shifts) {
        this(vehicleNumber, vehicleType, maxRow, rowWidth);

        FieldValidator.validateObjectNotNull(trips, "Trips list");
        for (Trip trip : trips) {
            addTrip(trip);
        }

        FieldValidator.validateObjectNotNull(shifts, "Shifts list");
        for (Shift shift : shifts) {
            addShift(shift);
        }
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = FieldValidator.validateNullOrEmptyString(vehicleNumber, "Vehicle Number");
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = FieldValidator.validateObjectNotNull(vehicleType, "Vehicle Type");
    }

    public int getMaxRow() {
        return maxRow;
    }

    public void setMaxRow(int maxRow) {
        this.maxRow = FieldValidator.validatePositiveNumber(maxRow, "Max Row");
    }

    public int getRowWidth() {
        return rowWidth;
    }

    public void setRowWidth(int rowWidth) {
        this.rowWidth = FieldValidator.validatePositiveNumber(rowWidth, "Row Width");
    }

    void addSeat(Seat seat) {
        if (seat.getVehicle() != null) throw new IllegalArgumentException("Can not reassign seat to vehicle");

        FieldValidator.validateSeatDimension(seat, this);
        FieldValidator.validateSeatNotDuplicate(seat, this);

        this.seats.add(seat);
    }

    public Set<Seat> getSeats() {
        return new HashSet<>(seats);
    }


    void addTrip(Trip trip) {
        FieldValidator.validateObjectNotNull(trip, "Trip");
        this.trips.add(trip);
    }

    void removeTrip(Trip trip) {
        FieldValidator.validateObjectNotNull(trip, "Trip");
        if (!this.trips.contains(trip)) return;
        this.trips.remove(trip);
    }

    public Set<Trip> getTrips() {
        return new HashSet<>(trips);
    }

    void addShift(Shift shift) {
        FieldValidator.validateObjectNotNull(shift, "Shift");
        this.shifts.add(shift);
    }

    void removeShift(Shift shift) {
        FieldValidator.validateObjectNotNull(shift, "Shift");

        if (!this.shifts.contains(shift)) return;

        if (this.shifts.size() == 1) {
            throw new IllegalStateException("Cannot remove shift. Vehicle must have at least one shift");
        }
        this.shifts.remove(shift);
    }

    public Set<Shift> getShifts() {
        return new HashSet<>(shifts);
    }
}
