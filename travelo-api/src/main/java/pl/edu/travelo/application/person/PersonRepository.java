package pl.edu.travelo.application.person;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.travelo.domain.model.Person;

interface PersonRepository extends JpaRepository<Person, Long> {
}
