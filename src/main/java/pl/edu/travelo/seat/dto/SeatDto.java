package pl.edu.travelo.seat.dto;

public record SeatDto(
        long id,
        int seatNumber,
        int row,
        boolean isBooked
) {
}
