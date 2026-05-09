package pl.edu.travelo.staff.model;

import pl.edu.travelo.person.model.Person;
import pl.edu.travelo.validation.FieldValidator;

import java.time.LocalDate;

public class Staff extends Person {
    private LocalDate hireDate;
    private double salary;
    private ContractType contractType;
    private Staff manager;

    public Staff(String firstName, String lastName, String phoneNumber, String email, String password, LocalDate birthDate,
                 LocalDate hireDate, double salary, ContractType contractType, Staff manager) {

        super(firstName, lastName, phoneNumber, email, password, birthDate);
        setHireDate(hireDate);
        setSalary(salary);
        setContractType(contractType);

//        assignManager(manager);
    }

    public Staff(String firstName, String lastName, String phoneNumber, String email, String password, LocalDate birthDate,
                 LocalDate hireDate, double salary, ContractType contractType) {

        super(firstName, lastName, phoneNumber, email, password, birthDate);
        setHireDate(hireDate);
        setSalary(salary);
        setContractType(contractType);
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = FieldValidator.validateDateNotInTheFuture(hireDate, "Hire Date");
    }

    public void setSalary(double salary) {
        this.salary = FieldValidator.validatePositiveNumber(salary, "Salary");
    }

    public void setContractType(ContractType contractType) {
        this.contractType = FieldValidator.validateObjectNotNull(contractType, "Contract Type");
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public double getSalary() {
        return salary;
    }

    public ContractType getContractType() {
        return contractType;
    }
}
