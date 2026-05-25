package pl.edu.travelo.domain.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import pl.edu.travelo.domain.enums.ReservationStatus;
import pl.edu.travelo.domain.validation.FieldValidator;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "reservation")
public class Reservation {
    private static double currencyPerPoint = 0.01;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private UUID reservationNumber;

    @Column(name = "time", nullable = false)
    private LocalDateTime reservationTime;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    @Column(length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @Column(nullable = false)
    private int loyaltyPointsUsed;


    @OneToMany(mappedBy = "reservation", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private final Set<Payment> payments = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;

    @ManyToOne
    @JoinColumn(name = "discount_id")
    private Discount discount;

    @ManyToMany
    @JoinTable(
            name = "seat_reservation",
            joinColumns = @JoinColumn(name = "reservation_id"),
            inverseJoinColumns = @JoinColumn(name = "seat_id")
    )
    private final Set<Seat> seats = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    public Reservation(LocalDateTime reservationTime, ReservationStatus status, Trip trip, Customer customer, Seat seat,
                       int loyaltyPointsUsed) {
        this.reservationNumber = UUID.randomUUID();
        this.reservationTime = FieldValidator.validateObjectNotNull(reservationTime, "Reservation Time");
        this.status = FieldValidator.validateObjectNotNull(status, "Status");
        this.expiresAt = LocalDateTime.now().plusMinutes(10);
        this.loyaltyPointsUsed = FieldValidator.validateNonNegativeNumber(loyaltyPointsUsed, "loyalty points used");

        assignTrip(trip);
        assignCustomer(customer);
        addSeat(seat);
    }

    public Reservation(LocalDateTime reservationTime, ReservationStatus status, Trip trip, Customer customer,
                       Seat seat, Discount discount, int loyaltyPointsUsed) {
        this(reservationTime, status, trip, customer, seat, loyaltyPointsUsed);
        assignDiscount(discount);
    }

    public Reservation(LocalDateTime reservationTime, ReservationStatus status, Trip trip, Customer customer,
                       Set<Seat> seats, Discount discount, int loyaltyPointsUsed) {

        this(reservationTime, status, trip, customer, seats, loyaltyPointsUsed);
        assignDiscount(discount);
    }

    public Reservation(LocalDateTime reservationTime, ReservationStatus status, Trip trip, Customer customer,
                       Set<Seat> seats, int loyaltyPointsUsed) {

        this.reservationNumber = UUID.randomUUID();
        this.reservationTime = FieldValidator.validateObjectNotNull(reservationTime, "Reservation Time");
        this.status = FieldValidator.validateObjectNotNull(status, "Status");
        this.expiresAt = LocalDateTime.now().plusMinutes(10);
        this.loyaltyPointsUsed = FieldValidator.validateNonNegativeNumber(loyaltyPointsUsed, "loyalty points used");
        FieldValidator.validateObjectNotNull(seats, "Seat list");

        assignTrip(trip);
        assignCustomer(customer);
        for (Seat s : seats) {
            addSeat(s);
        }
    }


    protected Reservation() {
    }

    public void setStatus(ReservationStatus status) {
        this.status = FieldValidator.validateObjectNotNull(status, "Status");
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public UUID getReservationNumber() {
        return reservationNumber;
    }

    public LocalDateTime getReservationTime() {
        return reservationTime;
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

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public static double getCurrencyPerPoint() {
        return currencyPerPoint;
    }

    public static void setCurrencyPerPoint(double currencyPerPoint) {
        Reservation.currencyPerPoint = FieldValidator.validateNonNegativeNumber(currencyPerPoint, "currency per point");
    }

    public int getLoyaltyPointsUsed() {
        return loyaltyPointsUsed;
    }

    public void setLoyaltyPointsUsed(int loyaltyPointsUsed) {
        this.loyaltyPointsUsed = FieldValidator.validateNonNegativeNumber(loyaltyPointsUsed, "loyal points used");
    }

    public double getTotalPrice() {
        double total = trip.getPrice();
        for (Seat seat : seats) {
            total += seat.getPrice();
        }
        double discountAmount = discount == null ? 0.0 : total * discount.getDiscountAmount();
        total -= discountAmount;

        double loyaltyDiscount = loyaltyPointsUsed * currencyPerPoint;
        total -= loyaltyDiscount;

        return Math.max(0.0, total);
    }
}
