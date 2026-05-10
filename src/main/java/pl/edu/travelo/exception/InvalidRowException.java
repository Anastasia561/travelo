package pl.edu.travelo.exception;

public class InvalidRowException extends RuntimeException {
    public InvalidRowException() {
        super("Seat row exceeds vehicle max rows");
    }
}
