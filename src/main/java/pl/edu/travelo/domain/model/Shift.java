package pl.edu.travelo.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import pl.edu.travelo.validation.FieldValidator;

import java.time.LocalDateTime;

@Entity
@Table(name = "shift")
public class Shift {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;


    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "staff_id", nullable = false)
    private Staff staff;

    public Shift(LocalDateTime startTime, LocalDateTime endTime, Vehicle vehicle, Staff staff) {
        FieldValidator.validateDateTimeNotInThePast(startTime, "Start Time");
        FieldValidator.validateDateTimeNotInThePast(endTime, "End Time");
        FieldValidator.validateDateTimeRange(startTime, endTime);
        this.startTime = startTime;
        this.endTime = endTime;

        assignVehicle(vehicle);
        assignStaff(staff);
    }

    protected Shift() {
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        FieldValidator.validateDateTimeNotInThePast(startTime, "Start Time");
        FieldValidator.validateDateTimeRange(startTime, endTime);
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        FieldValidator.validateDateTimeNotInThePast(endTime, "End Time");
        FieldValidator.validateDateTimeRange(startTime, endTime);
        this.endTime = endTime;
    }

    public void assignVehicle(Vehicle newVehicle) {
        FieldValidator.validateObjectNotNull(newVehicle, "Vehicle");

        if (this.vehicle == newVehicle) return;

        if (this.vehicle != null) {
            this.vehicle.removeShift(this);
        }

        this.vehicle = newVehicle;
        this.vehicle.addShift(this);
    }

    public void assignStaff(Staff newStaff) {
        FieldValidator.validateObjectNotNull(newStaff, "Staff");

        if (this.staff == newStaff) return;

        if (this.staff != null) {
            this.staff.removeShift(this);
        }

        this.staff = newStaff;
        this.staff.addShift(this);
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Staff getStaff() {
        return staff;
    }
}
