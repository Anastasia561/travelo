package pl.edu.travelo.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import pl.edu.travelo.domain.enums.PaymentMethod;
import pl.edu.travelo.domain.enums.PaymentStatus;
import pl.edu.travelo.domain.validation.FieldValidator;

import java.util.UUID;

@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentMethod method;

    @Column(length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Column(nullable = false)
    private UUID transactionId;

    @ManyToOne
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;

    public Payment(PaymentMethod paymentMethod, PaymentStatus paymentStatus, UUID transactionId, Reservation reservation) {
        setMethod(paymentMethod);
        setStatus(paymentStatus);
        this.transactionId = FieldValidator.validateObjectNotNull(transactionId, "Transaction ID");

        FieldValidator.validateObjectNotNull(reservation, "Reservation");
        reservation.addPayment(this);
        this.reservation = reservation;
    }

    protected Payment() {
    }

    public PaymentMethod getPaymentMethod() {
        return method;
    }

    public void setMethod(PaymentMethod paymentMethod) {
        this.method = FieldValidator.validateObjectNotNull(paymentMethod, "Payment Method");
    }

    public PaymentStatus getPaymentStatus() {
        return status;
    }

    public void setStatus(PaymentStatus paymentStatus) {
        this.status = FieldValidator.validateObjectNotNull(paymentStatus, "Payment Status");
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public Reservation getReservation() {
        return reservation;
    }
}
