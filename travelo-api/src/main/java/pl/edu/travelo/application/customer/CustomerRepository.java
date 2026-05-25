package pl.edu.travelo.application.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.travelo.domain.model.Customer;

interface CustomerRepository extends JpaRepository<Customer, Long> {
}
