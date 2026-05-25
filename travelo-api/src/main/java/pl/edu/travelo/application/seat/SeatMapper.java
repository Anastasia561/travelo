package pl.edu.travelo.application.seat;

import org.springframework.stereotype.Component;
import pl.edu.travelo.application.seat.dto.SeatDto;
import pl.edu.travelo.application.seat.dto.SeatReservationDto;
import pl.edu.travelo.domain.model.Seat;

@Component
public class SeatMapper {
    public SeatDto toDto(Seat seat, long tripId) {
        return new SeatDto(
                seat.getId(),
                seat.getSeatNumber(),
                seat.getPrice(),
                seat.getRow(),
                seat.isBooked(tripId));
    }

    public SeatReservationDto toReservationDto(Seat seat) {
        return new SeatReservationDto(seat.getSeatNumber(), seat.getRow());
    }
}
