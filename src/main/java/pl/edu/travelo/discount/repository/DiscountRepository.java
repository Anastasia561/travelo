package pl.edu.travelo.discount.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.travelo.domain.model.Discount;

import java.util.Optional;

public interface DiscountRepository extends JpaRepository<Discount, Long> {
    Optional<Discount> findDiscountByPromeCode(String promeCode);
}
