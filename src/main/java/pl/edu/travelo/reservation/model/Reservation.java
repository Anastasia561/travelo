package pl.edu.travelo.reservation.model;

import pl.edu.travelo.payment.model.Payment;
import pl.edu.travelo.payment.model.PaymentMethod;
import pl.edu.travelo.payment.model.PaymentStatus;
import pl.edu.travelo.validation.FieldValidator;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;

public class Reservation {
    private final UUID reservationNumber;
    private final LocalDateTime reservationTime;
    private ReservationStatus status;
    private Set<Payment> payments;

    public Reservation(LocalDateTime reservationTime, ReservationStatus status) {
        this.reservationNumber = UUID.randomUUID();
        this.reservationTime = FieldValidator.validateDateTimeNotInThePast(reservationTime, "Reservation Time");
        this.status = FieldValidator.validateObjectNotNull(status, "Status");
    }

    public void setStatus(ReservationStatus status) {
        this.status = FieldValidator.validateObjectNotNull(status, "Status");
    }

    public UUID getReservationNumber() {
        return reservationNumber;
    }

    public LocalDateTime getReservationTime() {
        return reservationTime;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public Payment addPayment(PaymentMethod paymentMethod, PaymentStatus paymentStatus, String transactionId) {
        Payment newPayment = new Payment(paymentMethod, paymentStatus, transactionId, this);
        this.payments.add(newPayment);
        return newPayment;
    }

    public Set<Payment> getPayments() {
        return Collections.unmodifiableSet(payments);
    }
}
