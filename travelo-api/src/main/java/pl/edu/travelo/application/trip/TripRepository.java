package pl.edu.travelo.application.trip;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.edu.travelo.domain.model.Trip;

import java.util.Optional;

interface TripRepository extends JpaRepository<Trip, Long> {

    @EntityGraph(attributePaths = {"startCity", "destination.city", "vehicle"})
    @Query("SELECT t FROM Trip t")
    Page<Trip> findAllWithDetails(Pageable pageable);

    @EntityGraph(attributePaths = {
            "vehicle",
            "vehicle.seats",
            "vehicle.seats.reservations"
    })
    @Query("SELECT t FROM Trip t WHERE t.id = :id")
    Optional<Trip> findByIdWithVehicleSeatsAndReservations(@Param("id") Long id);
}
