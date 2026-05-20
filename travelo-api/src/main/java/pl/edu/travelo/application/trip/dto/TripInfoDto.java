package pl.edu.travelo.application.trip.dto;

import pl.edu.travelo.application.destination.dto.DestinationResponseDto;
import pl.edu.travelo.application.vehicle.dto.VehicleBookingInfoResponseDto;

import java.time.LocalDateTime;

public record TripInfoDto(
        long id,
        String currency,
        LocalDateTime departureTime,
        LocalDateTime arrivalTime,
        double price,
        int availablePlaceCount,
        boolean isFull,
        boolean isCancelled,
        String startCityName,
        DestinationResponseDto destination,
        VehicleBookingInfoResponseDto vehicleInfo
) {
}
