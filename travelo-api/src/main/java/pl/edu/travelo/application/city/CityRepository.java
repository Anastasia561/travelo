package pl.edu.travelo.application.city;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.travelo.domain.model.City;

interface CityRepository extends JpaRepository<City, Long> {
}
