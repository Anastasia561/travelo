package pl.edu.travelo.destination.dto;

import pl.edu.travelo.vehicle.dto.VehicleResponseDto;

import java.util.Set;

public record DestinationInfoResponseDto(
        String name,
        String description,
        String cityName,
        String countryName,
        Set<VehicleResponseDto> vehicles
) {
}
