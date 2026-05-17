package pl.edu.travelo.application.vehicle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.travelo.domain.model.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
}
