package pl.edu.travelo.discount.model;

import pl.edu.travelo.exception.InvalidDiscountException;
import pl.edu.travelo.validation.FieldValidator;

import java.util.Optional;

public abstract class Discount {
    private double amount;
    private String promeCode;
    private IAgeGroupDiscount ageGroupDiscount;
    private ISpecialConditionDiscount specialConditionDiscount;

    private Discount(double amount, String promoCode) {
        setDiscountAmount(amount);
        setPromoCode(promoCode);
    }

    public Discount(double discountAmount, String promoCode, String ageGroupDiscountDescription, AgeGroup ageGroup) {
        this(discountAmount, promoCode);
        this.ageGroupDiscount = new AgeGroupDiscount(ageGroupDiscountDescription, ageGroup);
    }

    public Discount(double discountAmount, String promoCode, String specialConditionDescription) {
        this(discountAmount, promoCode);
        this.specialConditionDiscount = new SpecialConditionDiscount(specialConditionDescription);
    }

    public void setDiscountAmount(double amount) {
        if (amount > 0 && amount < 1) {
            this.amount = amount;
        } else {
            throw new InvalidDiscountException();
        }
    }

    public void setPromoCode(String promeCode) {
        this.promeCode = FieldValidator.validateNullOrEmptyString(promeCode, "Prome Code");
    }

    public double getDiscountAmount() {
        return amount;
    }

    public String getPromoCode() {
        return promeCode;
    }

    public Optional<IAgeGroupDiscount> getAgeGroupDiscount() {
        return Optional.ofNullable(ageGroupDiscount);
    }

    public Optional<ISpecialConditionDiscount> getSpecialConditionDiscount() {
        return Optional.ofNullable(specialConditionDiscount);
    }

    private static class AgeGroupDiscount implements IAgeGroupDiscount {
        private String description;
        private AgeGroup group;

        public AgeGroupDiscount(String description, AgeGroup group) {
            setDescription(description);
            setGroup(group);
        }

        @Override
        public void setDescription(String description) {
            this.description = FieldValidator.validateNullOrEmptyString(description, "Description");
        }

        @Override
        public void setGroup(AgeGroup group) {
            this.group = FieldValidator.validateObjectNotNull(group, "Group");
        }

        @Override
        public String getDescription() {
            return description;
        }

        @Override
        public AgeGroup getGroup() {
            return group;
        }
    }

    private static class SpecialConditionDiscount implements ISpecialConditionDiscount {
        private String conditionDescription;

        public SpecialConditionDiscount(String conditionDescription) {
            setConditionDescription(conditionDescription);
        }

        @Override
        public void setConditionDescription(String conditionDescription) {
            this.conditionDescription = FieldValidator.validateNullOrEmptyString(conditionDescription, "Condition Description");
        }

        @Override
        public String getConditionDescription() {
            return conditionDescription;
        }
    }
}
