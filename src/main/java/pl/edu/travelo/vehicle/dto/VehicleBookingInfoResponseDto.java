package pl.edu.travelo.vehicle.dto;

import pl.edu.travelo.domain.enums.VehicleType;
import pl.edu.travelo.seat.dto.SeatDto;

import java.util.Set;

public record VehicleBookingInfoResponseDto(
        String number,
        VehicleType type,
        int maxRow,
        int rowWidth,
        Set<SeatDto> seats
) {
}
