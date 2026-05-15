package pl.edu.travelo.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import pl.edu.travelo.domain.enums.AgeGroup;
import pl.edu.travelo.domain.enums.DiscountType;
import pl.edu.travelo.validation.FieldValidator;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "discount")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private double amount;

    @Column(length = 10, nullable = false)
    private String promeCode;

    @Column(length = 30, nullable = false)
    @Enumerated(EnumType.STRING)
    private DiscountType discountType;

    @Column(length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private AgeGroup ageGroup;

    @Column(length = 100, nullable = false)
    private String conditionDescription;


    @OneToMany(mappedBy = "discount")
    private final Set<Reservation> reservations = new HashSet<>();

    private Discount(double amount, String promoCode) {
        setDiscountAmount(amount);
        setPromoCode(promoCode);
    }

    public Discount(double discountAmount, String promoCode, AgeGroup ageGroup) {
        this(discountAmount, promoCode);
        this.ageGroup = FieldValidator.validateObjectNotNull(ageGroup, "Age Group");
        this.discountType = DiscountType.AGE_GROUP_DISCOUNT;
    }

    public Discount(double discountAmount, String promoCode, String description) {
        this(discountAmount, promoCode);
        this.conditionDescription = FieldValidator.validateNullOrEmptyString(description, "Description");
        this.discountType = DiscountType.SPECIAL_CONDITION_DISCOUNT;
    }

    public Discount() {
    }

    public void setDiscountAmount(double amount) {
        if (amount > 0 && amount < 1) {
            this.amount = amount;
        } else {
            throw new IllegalArgumentException("Discount amount must be between 0 and 1");
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

    public AgeGroup getAgeGroup() {
        if (discountType != DiscountType.AGE_GROUP_DISCOUNT) {
            throw new IllegalStateException("Discount is not of type age group discount");
        }
        return ageGroup;
    }

    public String getConditionDescription() {
        if (discountType != DiscountType.SPECIAL_CONDITION_DISCOUNT) {
            throw new IllegalStateException("Discount is not of type special condition discount");
        }
        return conditionDescription;
    }

    public void setAgeGroup(AgeGroup ageGroup) {
        if (discountType != DiscountType.AGE_GROUP_DISCOUNT) {
            throw new IllegalStateException("Discount is not of type age group discount");
        }
        this.ageGroup = FieldValidator.validateObjectNotNull(ageGroup, "Age Group");
    }

    public void setConditionDescription(String conditionDescription) {
        if (discountType != DiscountType.SPECIAL_CONDITION_DISCOUNT) {
            throw new IllegalStateException("Discount is not of type special condition discount");
        }
        this.conditionDescription = FieldValidator.validateNullOrEmptyString(conditionDescription, "Condition Description");
    }

    public void addReservation(Reservation reservation) {
        FieldValidator.validateObjectNotNull(reservation, "Reservation");
        this.reservations.add(reservation);
    }

    public void removeReservation(Reservation reservation) {
        FieldValidator.validateObjectNotNull(reservation, "Reservation");
        if (!this.reservations.contains(reservation)) return;
        this.reservations.remove(reservation);
    }
}
