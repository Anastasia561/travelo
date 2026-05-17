package pl.edu.travelo.trip.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.travelo.domain.model.Trip;

public interface TripRepository extends JpaRepository<Trip, Long> {
}
