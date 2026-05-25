package pl.edu.travelo.application.staff;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.travelo.domain.model.Staff;

interface StaffRepository extends JpaRepository<Staff, Long> {
}
