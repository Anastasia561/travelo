package pl.edu.travelo.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import pl.edu.travelo.validation.FieldValidator;

import java.time.LocalDate;
import java.util.Optional;

@Entity
@Table(name = "person")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 50, nullable = false)
    private String firstName;

    @Column(length = 50, nullable = false)
    private String lastName;

    @Column(length = 20)
    private String phoneNumber;

    @Column(length = 100, nullable = false)
    private String email;

    @Column(length = 250, nullable = false)
    private String password;

    @Column(nullable = false)
    private LocalDate birthdate;

    public Person(String firstName, String lastName, String phoneNumber, String email, String password, LocalDate birthDate) {
        setFirstName(firstName);
        setLastName(lastName);
        setPhoneNumber(phoneNumber);
        setEmail(email);
        setPassword(password);
        setBirthDate(birthDate);
    }

    public Person() {
    }

    public void setFirstName(String firstName) {
        this.firstName = FieldValidator.validateNullOrEmptyString(firstName, "First Name");
    }

    public void setLastName(String lastName) {
        this.lastName = FieldValidator.validateNullOrEmptyString(lastName, "Last Name");
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = FieldValidator.validatePhoneNumber(phoneNumber);
    }

    public void setEmail(String email) {
        this.email = FieldValidator.validateEmail(email);
    }

    public void setPassword(String password) {
        this.password = FieldValidator.validateNullOrEmptyString(password, "Password");
    }

    public void setBirthDate(LocalDate birthdate) {
        this.birthdate = FieldValidator.validateDateNotInTheFuture(birthdate, "Birth Date");
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Optional<String> getPhoneNumber() {
        return Optional.ofNullable(phoneNumber);
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public LocalDate getBirthDate() {
        return birthdate;
    }
}