package pl.edu.travelo.application.seat.service;

import pl.edu.travelo.domain.model.Seat;

import java.util.Set;

public interface SeatService {
    void lockSeat(long id);

    Set<Seat> findAllByIds(Set<Long> ids);
}
