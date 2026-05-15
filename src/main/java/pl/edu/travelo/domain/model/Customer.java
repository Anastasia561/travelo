package pl.edu.travelo.domain.model;

import pl.edu.travelo.validation.FieldValidator;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class Customer extends Person {
    private int loyaltyPoints = 0;
    private final Map<UUID, Reservation> reservations = new HashMap<>();

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

    void addReservation(Reservation reservation) {
        FieldValidator.validateObjectNotNull(reservation, "Reservation");
        reservations.put(reservation.getReservationNumber(), reservation);
    }

    public Optional<Reservation> getReservation(UUID key) {
        FieldValidator.validateObjectNotNull(key, "Key");
        return Optional.ofNullable(reservations.get(key));
    }

    void removeReservation(Reservation reservation) {
        FieldValidator.validateObjectNotNull(reservation, "Reservation");
        reservations.remove(reservation.getReservationNumber());
    }

    public Map<UUID, Reservation> getReservations() {
        return new HashMap<>(reservations);
    }
}
