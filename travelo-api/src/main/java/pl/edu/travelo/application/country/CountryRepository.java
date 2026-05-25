package pl.edu.travelo.application.country;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.travelo.domain.model.Country;

interface CountryRepository extends JpaRepository<Country, Long> {
}
