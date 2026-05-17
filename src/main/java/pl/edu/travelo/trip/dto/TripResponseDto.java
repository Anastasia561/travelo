package pl.edu.travelo.trip.dto;

import pl.edu.travelo.domain.enums.VehicleType;

import java.time.LocalDateTime;

public record TripResponseDto(
        String currency,
        LocalDateTime departureTime,
        LocalDateTime arrivalTime,
        double price,
        int availablePlaceCount,
        boolean isFull,
        boolean isCancelled,
        String startCityName,
        String destinationName,
        VehicleType vehicleType
) {
}
