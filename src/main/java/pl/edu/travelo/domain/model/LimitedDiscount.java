package pl.edu.travelo.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import pl.edu.travelo.domain.enums.AgeGroup;
import pl.edu.travelo.validation.FieldValidator;

import java.time.LocalDateTime;

@Entity
@Table(name = "limited_discount")
@PrimaryKeyJoinColumn(name = "id")
public class LimitedDiscount extends Discount {
    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
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

    protected LimitedDiscount() {
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
