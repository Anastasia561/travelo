package pl.edu.travelo.person.model;

import pl.edu.travelo.validation.FieldValidator;

import java.time.LocalDate;
import java.util.Optional;

public abstract class Person {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String password;
    private LocalDate birthdate;

    public Person(String firstName, String lastName, String phoneNumber, String email, String password, LocalDate birthDate) {
        setFirstName(firstName);
        setLastName(lastName);
        setPhoneNumber(phoneNumber);
        setEmail(email);
        setPassword(password);
        setBirthDate(birthDate);
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