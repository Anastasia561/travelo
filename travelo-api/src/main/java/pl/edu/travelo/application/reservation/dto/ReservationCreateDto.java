package pl.edu.travelo.application.reservation.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record ReservationCreateDto(
        @NotNull(message = "Trip ID is required")
        Long tripId,

        @Size(max = 10, message = "Promo code cannot exceed 10 characters")
        String promeCode,

        @NotNull(message = "Loyalty points are required")
        @Min(value = 0, message = "Loyalty points cannot be negative")
        Integer loyaltyPoints,

        @NotEmpty(message = "At least one seat must be selected")
        Set<@NotNull(message = "Seat ID cannot be null") Long> seatIds
) {
}
