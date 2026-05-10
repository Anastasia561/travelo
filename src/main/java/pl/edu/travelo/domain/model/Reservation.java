package pl.edu.travelo.domain.model;

import pl.edu.travelo.domain.enums.ReservationStatus;
import pl.edu.travelo.validation.FieldValidator;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class Reservation {
    private final UUID reservationNumber;
    private final LocalDateTime reservationTime;
    private ReservationStatus status;

    private final Set<Payment> payments = new HashSet<>();
    private Trip trip;
    private Discount discount;
    private final Set<Seat> seats = new HashSet<>();
    private Customer customer;

    public Reservation(LocalDateTime reservationTime, ReservationStatus status, Trip trip, Customer customer) {
        this.reservationNumber = UUID.randomUUID();
        this.reservationTime = FieldValidator.validateObjectNotNull(reservationTime, "Reservation Time");
        this.status = FieldValidator.validateObjectNotNull(status, "Status");

        assignTrip(trip);
    }

    public Reservation(LocalDateTime reservationTime, ReservationStatus status, Trip trip, Customer customer,
                       Set<Seat> seats, Discount discount) {
        this(reservationTime, status, trip, customer);

        FieldValidator.validateObjectNotNull(seats, "Seats list");
        for (Seat seat : seats) {
            addSeat(seat);
        }
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

    void addPayment(Payment payment) {
        if (payment.getReservation() != null) {
            throw new IllegalStateException("Cannot reassign payment to different reservation");
        }
        this.payments.add(payment);
    }

    public Set<Payment> getPayments() {
        return new HashSet<>(payments);
    }

    public void assignTrip(Trip newTrip) {
        FieldValidator.validateObjectNotNull(newTrip, "Trip");

        if (this.trip == newTrip) return;

        if (this.trip != null) {
            this.trip.removeReservation(this);
        }

        this.trip = newTrip;
        this.trip.addReservation(this);
    }

    public Trip getTrip() {
        return trip;
    }

    public void addSeat(Seat seat) {
        FieldValidator.validateObjectNotNull(seat, "Seat");

        if (this.seats.contains(seat)) return;

        this.seats.add(seat);
        seat.addReservation(this);
    }

    public void removeSeat(Seat seat) {
        FieldValidator.validateObjectNotNull(seat, "Seat");

        if (!this.seats.contains(seat)) return;
        if (this.seats.size() == 1) {
            throw new IllegalStateException("Cannot remove seat " + seat.getSeatNumber() +
                    " . Reservation must have at least one seat.");
        }

        this.seats.remove(seat);
        seat.removeReservation(this);
    }

    public Set<Seat> getSeats() {
        return new HashSet<>(seats);
    }

    public void assignDiscount(Discount discount) {
        FieldValidator.validateObjectNotNull(discount, "Discount");
        this.discount = discount;
        this.discount.addReservation(this);
    }

    public void removeDiscount() {
        if (this.discount != null) {
            this.discount.removeReservation(this);
            this.discount = null;
        }
    }

    public Optional<Discount> getDiscount() {
        return Optional.ofNullable(discount);
    }

    public void assignCustomer(Customer customer) {
        FieldValidator.validateObjectNotNull(customer, "Customer");
        if (this.customer != null) {
            this.customer.removeReservation(this);
        }

        this.customer = customer;

        customer.addReservation(this);
    }

    public Customer getCustomer() {
        return this.customer;
    }
}
