package pl.edu.travelo.application.payment;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.travelo.domain.model.Payment;

interface PaymentRepository extends JpaRepository<Payment, Long> {
}
