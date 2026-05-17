package pl.edu.travelo.destination.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.travelo.domain.model.Destination;

public interface DestinationRepository extends JpaRepository<Destination, Long> {
}
