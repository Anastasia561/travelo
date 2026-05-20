package pl.edu.travelo.application.trip.dto;

import pl.edu.travelo.application.destination.dto.DestinationResponseDto;
import pl.edu.travelo.domain.enums.VehicleType;

import java.time.LocalDateTime;

public record TripReservationInfoDto(
        long id,
        LocalDateTime departureTime,
        LocalDateTime arrivalTime,
        String startCity,
        DestinationResponseDto destination,
        VehicleType vehicleType,
        String vehicleNumber
) {
}
