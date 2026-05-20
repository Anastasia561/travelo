package pl.edu.travelo.application.vehicle.dto;

import pl.edu.travelo.domain.enums.VehicleType;

public record VehicleResponseDto(
        String number,
        VehicleType type,
        int maxRow,
        int rowWidth
) {
}
