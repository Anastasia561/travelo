package pl.edu.travelo.application.trip.dto;

import pl.edu.travelo.domain.enums.VehicleType;

import java.time.LocalDateTime;

public record TripResponseDto(
        long id,
        LocalDateTime departureTime,
        LocalDateTime arrivalTime,
        double price,
        int availablePlaceCount,
        boolean isFull,
        boolean isCancelled,
        String startCityName,
        String destinationCityName,
        String destinationName,
        VehicleType vehicleType
) {
}
