package pl.edu.travelo.trip.dto;

import pl.edu.travelo.destination.dto.DestinationResponseDto;
import pl.edu.travelo.vehicle.dto.VehicleBookingInfoResponseDto;

import java.time.LocalDateTime;

public record TripInfoDto(
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
