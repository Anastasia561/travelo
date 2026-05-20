package pl.edu.travelo.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import pl.edu.travelo.domain.enums.ContractType;
import pl.edu.travelo.domain.validation.FieldValidator;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "staff")
@PrimaryKeyJoinColumn(name = "id")
public class Staff extends Person {
    @Column(nullable = false)
    private LocalDate hireDate;

    @Column(nullable = false)
    private double salary;

    @Column(length = 30, nullable = false)
    @Enumerated(EnumType.STRING)
    private ContractType contractType;

    @OneToMany(mappedBy = "staff")
    private final Set<Shift> shifts = new HashSet<>();

    public Staff(String firstName, String lastName, String phoneNumber, String email, String password, LocalDate birthDate,
                 LocalDate hireDate, double salary, ContractType contractType) {

        super(firstName, lastName, phoneNumber, email, password, birthDate);
        setHireDate(hireDate);
        setSalary(salary);
        setContractType(contractType);
    }

    public Staff(String firstName, String lastName, String phoneNumber, String email, String password, LocalDate birthDate,
                 LocalDate hireDate, double salary, ContractType contractType, Set<Shift> shifts) {

        this(firstName, lastName, phoneNumber, email, password, birthDate, hireDate, salary, contractType);

        FieldValidator.validateObjectNotNull(shifts, "Shifts list");
        for (Shift shift : shifts) {
            addShift(shift);
        }
    }

    protected Staff() {
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

    void addShift(Shift shift) {
        FieldValidator.validateObjectNotNull(shift, "Shift");
        this.shifts.add(shift);
    }

    void removeShift(Shift shift) {
        FieldValidator.validateObjectNotNull(shift, "Shift");

        if (!this.shifts.contains(shift)) return;

        if (this.shifts.size() == 1) {
            throw new IllegalStateException("Cannot remove shift. Staff must have at least one shift");
        }
        this.shifts.remove(shift);
    }

    public Set<Shift> getShifts() {
        return new HashSet<>(shifts);
    }
}
