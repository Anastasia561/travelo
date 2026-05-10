package pl.edu.travelo.exception;

public class InvalidSeatNumberException extends RuntimeException {
    public InvalidSeatNumberException() {
        super("Seat number exceeds vehicle row width");
    }
}
