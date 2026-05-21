package pl.edu.travelo.application.seat.dto;

public record SeatDto(
        long id,
        int seatNumber,
        double price,
        int row,
        boolean isBooked
) {
}
