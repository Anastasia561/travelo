package pl.edu.travelo.exception;

public class InvalidEmailFormatException extends RuntimeException {
    public InvalidEmailFormatException() {
        super("Email format is invalid");
    }
}
