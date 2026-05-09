package pl.edu.travelo.vehicle.model;

import pl.edu.travelo.validation.FieldValidator;

public class Vehicle {
    private String vehicleNumber;
    private VehicleType vehicleType;

    public Vehicle(String vehicleNumber, VehicleType vehicleType) {
        setVehicleNumber(vehicleNumber);
        setVehicleType(vehicleType);
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
}
