package pl.edu.travelo.domain.model;

import pl.edu.travelo.domain.enums.AgeGroup;
import pl.edu.travelo.validation.FieldValidator;

import java.time.LocalDateTime;

public class LimitedDiscount extends Discount {
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public LimitedDiscount(LocalDateTime startTime, LocalDateTime endTime, double discountAmount, String promoCode, AgeGroup ageGroup) {
        super(discountAmount, promoCode, ageGroup);
        FieldValidator.validateDateTimeNotInThePast(endTime, "End Time");
        FieldValidator.validateDateTimeNotInThePast(startTime, "Start Time");
        FieldValidator.validateDateTimeRange(startTime, endTime);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public LimitedDiscount(LocalDateTime startTime, LocalDateTime endTime, double discountAmount, String promoCode, String specialConditionDescription) {
        super(discountAmount, promoCode, specialConditionDescription);
        FieldValidator.validateDateTimeNotInThePast(endTime, "End Time");
        FieldValidator.validateDateTimeNotInThePast(startTime, "Start Time");
        FieldValidator.validateDateTimeRange(startTime, endTime);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        FieldValidator.validateDateTimeNotInThePast(startTime, "Start Time");
        FieldValidator.validateDateTimeRange(startTime, endTime);
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        FieldValidator.validateDateTimeNotInThePast(endTime, "End Time");
        FieldValidator.validateDateTimeRange(startTime, endTime);
        this.endTime = endTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }
}
