package pl.edu.travelo.vehicle.dto;

import pl.edu.travelo.destination.dto.DestinationResponseDto;
import pl.edu.travelo.domain.enums.VehicleType;

import java.util.Set;

public record VehicleInfoResponseDto(
        String number,
        VehicleType type,
        int maxRow,
        int rowWidth,
        Set<DestinationResponseDto> destinations
) {
}
