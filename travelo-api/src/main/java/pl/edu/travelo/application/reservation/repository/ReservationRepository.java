package pl.edu.travelo.application.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.travelo.domain.model.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
