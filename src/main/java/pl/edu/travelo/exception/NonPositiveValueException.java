package pl.edu.travelo.exception;

public class NonPositiveValueException extends RuntimeException {
    public NonPositiveValueException(String fieldName) {
        super(fieldName + " must be a positive value ( > 0)");
    }
}
