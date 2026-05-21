package pl.edu.travelo.application.destination.dto;

import pl.edu.travelo.application.vehicle.dto.VehicleResponseDto;

import java.util.Set;

public record DestinationInfoResponseDto(
        String name,
        Set<VehicleResponseDto> vehicles
) {
}
