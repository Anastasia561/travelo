package pl.edu.travelo.application.shift;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.travelo.domain.model.Shift;

interface ShiftRepository extends JpaRepository<Shift, Long> {
}
