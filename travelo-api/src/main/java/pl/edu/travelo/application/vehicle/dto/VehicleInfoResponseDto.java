package pl.edu.travelo.application.vehicle.dto;

import pl.edu.travelo.application.destination.dto.DestinationResponseDto;
import pl.edu.travelo.domain.enums.VehicleType;

import java.util.Set;

public record VehicleInfoResponseDto(
        String number,
        VehicleType type,
        Set<DestinationResponseDto> destinations
) {
}
