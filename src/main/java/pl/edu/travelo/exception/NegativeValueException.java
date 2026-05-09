package pl.edu.travelo.exception;

public class NegativeValueException extends RuntimeException {
    public NegativeValueException(String fieldName) {
        super(fieldName + " must be a non-negative value ( >= 0)");
    }
}
