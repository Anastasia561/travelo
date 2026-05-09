package pl.edu.travelo.discount.model;

import pl.edu.travelo.validation.FieldValidator;

import java.time.DayOfWeek;
import java.util.Set;

public class RegularDiscount extends Discount {
    private Set<DayOfWeek> dayOfWeek;

    public RegularDiscount(Set<DayOfWeek> dayOfWeek, double discountAmount, String promeCode,
                           String specialConditionDescription) {
        super(discountAmount, promeCode, specialConditionDescription);
        setDayOfWeek(dayOfWeek);
    }

    public RegularDiscount(Set<DayOfWeek> dayOfWeek, double discountAmount, String promoCode,
                           String ageGroupDiscountDescription, AgeGroup ageGroup) {
        super(discountAmount, promoCode, ageGroupDiscountDescription, ageGroup);
        setDayOfWeek(dayOfWeek);
    }

    public void setDayOfWeek(Set<DayOfWeek> dayOfWeek) {
        this.dayOfWeek = FieldValidator.validateDayOfWeekList(dayOfWeek, "Day Of Week List");
    }

    public Set<DayOfWeek> getDayOfWeek() {
        return dayOfWeek;
    }
}
