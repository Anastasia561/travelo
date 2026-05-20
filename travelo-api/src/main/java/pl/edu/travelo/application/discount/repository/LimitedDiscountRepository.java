package pl.edu.travelo.application.discount.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.edu.travelo.domain.enums.AgeGroup;
import pl.edu.travelo.domain.model.LimitedDiscount;

import java.time.LocalDateTime;
import java.util.Set;

public interface LimitedDiscountRepository extends JpaRepository<LimitedDiscount, Long> {

    @Query("""
                SELECT d FROM LimitedDiscount d
                WHERE (d.startTime <= :now AND d.endTime >= :now)
                  AND (d.discountType != pl.edu.travelo.domain.enums.DiscountType.AGE_GROUP_DISCOUNT
                       OR d.ageGroup = :ageGroup)
            """)
    Set<LimitedDiscount> findAllByDateOrAge(
            @Param("now") LocalDateTime now,
            @Param("ageGroup") AgeGroup ageGroup
    );
}
