package pl.edu.travelo.application.discount.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.edu.travelo.domain.enums.AgeGroup;
import pl.edu.travelo.domain.model.RegularDiscount;

import java.time.DayOfWeek;
import java.util.Set;

public interface RegularDiscountRepository extends JpaRepository<RegularDiscount, Long> {

    @Query("""
                SELECT d FROM RegularDiscount d
                WHERE (:day MEMBER OF d.dayOfWeek)
                   AND (d.discountType != pl.edu.travelo.domain.enums.DiscountType.AGE_GROUP_DISCOUNT
                       OR d.ageGroup = :ageGroup)
            """)
    Set<RegularDiscount> findAllByDayOrAge(
            @Param("day") DayOfWeek day,
            @Param("ageGroup") AgeGroup ageGroup
    );
}
