package pl.edu.travelo.application.seat.dto;

public record SeatDto(
        long id,
        int seatNumber,
        int row,
        boolean isBooked
) {
}
