package pl.edu.travelo.domain.model;

import pl.edu.travelo.domain.enums.PaymentMethod;
import pl.edu.travelo.domain.enums.PaymentStatus;
import pl.edu.travelo.validation.FieldValidator;

public class Payment {
    private PaymentMethod method;
    private PaymentStatus status;
    private final String transactionId;
    private Reservation reservation;

    public Payment(PaymentMethod paymentMethod, PaymentStatus paymentStatus, String transactionId, Reservation reservation) {
        setMethod(paymentMethod);
        setStatus(paymentStatus);
        this.transactionId = FieldValidator.validateNullOrEmptyString(transactionId, "Transaction ID");

        FieldValidator.validateObjectNotNull(reservation, "Reservation");
        reservation.addPayment(this);
        this.reservation = reservation;
    }

    public PaymentMethod getPaymentMethod() {
        return method;
    }

    public PaymentStatus getPaymentStatus() {
        return status;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setMethod(PaymentMethod paymentMethod) {
        this.method = FieldValidator.validateObjectNotNull(paymentMethod, "Payment Method");
    }

    public void setStatus(PaymentStatus paymentStatus) {
        this.status = FieldValidator.validateObjectNotNull(paymentStatus, "Payment Status");
    }
}
