package pl.edu.travelo.domain.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import pl.edu.travelo.domain.validation.FieldValidator;
import pl.edu.travelo.domain.enums.VehicleType;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "vehicle")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 20, nullable = false)
    private String vehicleNumber;

    @Column(length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;

    @Column(nullable = false)
    private int maxRow;

    @Column(nullable = false)
    private int rowWidth;


    @OneToMany(mappedBy = "vehicle", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private final Set<Seat> seats = new HashSet<>();

    @OneToMany(mappedBy = "vehicle")
    private final Set<Trip> trips = new HashSet<>();

    @OneToMany(mappedBy = "vehicle")
    private final Set<Shift> shifts = new HashSet<>();

    public Vehicle(String vehicleNumber, VehicleType vehicleType, int maxRow, int rowWidth, int seatNumber, int row,
                   Staff staff, LocalDateTime startTime, LocalDateTime endTime) {
        setVehicleNumber(vehicleNumber);
        setVehicleType(vehicleType);
        setMaxRow(maxRow);
        setRowWidth(rowWidth);

        addSeat(seatNumber, row);
        FieldValidator.validateObjectNotNull(staff, "Staff");
        addShift(new Shift(startTime, endTime, this, staff));
    }

    public Vehicle(String vehicleNumber, VehicleType vehicleType, int maxRow, int rowWidth, int seatNumber, int row,
                   Set<Trip> trips, Set<Shift> shifts) {

        setVehicleNumber(vehicleNumber);
        setVehicleType(vehicleType);
        setMaxRow(maxRow);
        setRowWidth(rowWidth);
        addSeat(seatNumber, row);

        FieldValidator.validateObjectNotNull(trips, "Trips list");
        for (Trip trip : trips) {
            addTrip(trip);
        }

        FieldValidator.validateObjectNotNull(shifts, "Shifts list");
        for (Shift shift : shifts) {
            addShift(shift);
        }
    }

    protected Vehicle() {
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

    public void addSeat(int seatNumber, int row) {
        Seat newSeat = new Seat(seatNumber, row, this);
        FieldValidator.validateSeatDimension(newSeat, this);
        FieldValidator.validateSeatNotDuplicate(newSeat, this);

        this.seats.add(newSeat);
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
