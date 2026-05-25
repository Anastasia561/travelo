package pl.edu.travelo.application.discount;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.travelo.domain.model.Discount;

import java.util.Optional;

interface DiscountRepository extends JpaRepository<Discount, Long> {
    Optional<Discount> findDiscountByPromeCode(String promeCode);
}
