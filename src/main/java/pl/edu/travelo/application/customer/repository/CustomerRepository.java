package pl.edu.travelo.application.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.travelo.domain.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
