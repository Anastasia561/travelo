package pl.edu.travelo.application.reservation.mapper;

import org.springframework.stereotype.Component;
import pl.edu.travelo.application.reservation.dto.ReservationResponseDto;
import pl.edu.travelo.application.seat.dto.SeatReservationDto;
import pl.edu.travelo.application.seat.mapper.SeatMapper;
import pl.edu.travelo.application.trip.dto.TripReservationInfoDto;
import pl.edu.travelo.application.trip.mapper.TripMapper;
import pl.edu.travelo.domain.model.Reservation;
import pl.edu.travelo.domain.model.Seat;

import java.util.HashSet;
import java.util.Set;

@Component
public class ReservationMapper {
    private final TripMapper tripMapper;
    private final SeatMapper seatMapper;

    public ReservationMapper(TripMapper tripMapper, SeatMapper seatMapper) {
        this.tripMapper = tripMapper;
        this.seatMapper = seatMapper;
    }

    public ReservationResponseDto toResponseDto(Reservation reservation, double totalPrice) {
        TripReservationInfoDto tripDto = tripMapper.toReservationInfoDto(reservation.getTrip());
        Set<SeatReservationDto> seats = new HashSet<>();

        for (Seat seat : reservation.getSeats()) {
            seats.add(seatMapper.toReservationDto(seat));
        }

        return new ReservationResponseDto(reservation.getReservationNumber(), tripDto, seats, totalPrice);
    }
}
