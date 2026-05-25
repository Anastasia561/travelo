package pl.edu.travelo.application.reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.travelo.domain.model.Reservation;

interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
