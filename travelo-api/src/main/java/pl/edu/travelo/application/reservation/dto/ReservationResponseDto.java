package pl.edu.travelo.application.reservation.dto;

import pl.edu.travelo.application.seat.dto.SeatReservationDto;
import pl.edu.travelo.application.trip.dto.TripReservationInfoDto;

import java.util.Set;
import java.util.UUID;

public record ReservationResponseDto(
        UUID reservationNumber,
        TripReservationInfoDto trip,
        Set<SeatReservationDto> seats,
        double totalPrice
) {
}
