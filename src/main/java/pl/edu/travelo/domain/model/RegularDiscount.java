package pl.edu.travelo.domain.model;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import pl.edu.travelo.domain.enums.AgeGroup;
import pl.edu.travelo.validation.FieldValidator;

import java.time.DayOfWeek;
import java.util.Set;

@Entity
@Table(name = "regular_discount")
@PrimaryKeyJoinColumn(name = "id")
public class RegularDiscount extends Discount {
    @ElementCollection(targetClass = DayOfWeek.class)
    @CollectionTable(
            name = "discount_days",
            joinColumns = @JoinColumn(name = "discount_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week")
    private Set<DayOfWeek> dayOfWeek;

    public RegularDiscount(Set<DayOfWeek> dayOfWeek, double discountAmount, String promeCode,
                           String specialConditionDescription) {
        super(discountAmount, promeCode, specialConditionDescription);
        setDayOfWeek(dayOfWeek);
    }

    public RegularDiscount(Set<DayOfWeek> dayOfWeek, double discountAmount, String promoCode, AgeGroup ageGroup) {
        super(discountAmount, promoCode, ageGroup);
        setDayOfWeek(dayOfWeek);
    }

    protected RegularDiscount() {
    }

    public void setDayOfWeek(Set<DayOfWeek> dayOfWeek) {
        this.dayOfWeek = FieldValidator.validateDayOfWeekList(dayOfWeek, "Day Of Week List");
    }

    public Set<DayOfWeek> getDayOfWeek() {
        return dayOfWeek;
    }
}
