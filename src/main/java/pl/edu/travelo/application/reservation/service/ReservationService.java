package pl.edu.travelo.application.reservation.service;

import pl.edu.travelo.application.reservation.dto.ReservationCreateDto;
import pl.edu.travelo.application.reservation.dto.ReservationResponseDto;

public interface ReservationService {
    ReservationResponseDto create(ReservationCreateDto reservation, long customerId);
}
