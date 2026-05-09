package pl.edu.travelo.customer.model;

import pl.edu.travelo.person.model.Person;
import pl.edu.travelo.validation.FieldValidator;

import java.time.LocalDate;

public class Customer extends Person {
    private int loyaltyPoints = 0;

    public Customer(String firstName, String lastName, String phoneNumber, String email, String password, LocalDate birthDate,
                    int loyaltyPoints) {
        super(firstName, lastName, phoneNumber, email, password, birthDate);
        setLoyaltyPoints(loyaltyPoints);
    }

    public void setLoyaltyPoints(int loyaltyPoints) {
        this.loyaltyPoints = FieldValidator.validateNonNegativeNumber(loyaltyPoints, "Loyalty Points");
    }

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }
}
