package pl.edu.travelo.seat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.travelo.domain.model.Seat;

public interface SeatRepository extends JpaRepository<Seat,Long> {
}
