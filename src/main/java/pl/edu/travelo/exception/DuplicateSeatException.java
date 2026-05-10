package pl.edu.travelo.exception;

public class DuplicateSeatException extends RuntimeException {
    public DuplicateSeatException(String message) {
        super(message);
    }
}
