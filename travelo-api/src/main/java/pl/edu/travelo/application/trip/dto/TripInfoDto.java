package pl.edu.travelo.application.trip.dto;

import pl.edu.travelo.application.vehicle.dto.VehicleBookingInfoResponseDto;

public record TripInfoDto(
        double price,
        VehicleBookingInfoResponseDto vehicleInfo
) {
}
