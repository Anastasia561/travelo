package pl.edu.travelo.domain.model;

import pl.edu.travelo.validation.FieldValidator;

import java.time.LocalDateTime;

public class Shift {
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private Vehicle vehicle;
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
