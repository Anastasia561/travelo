package pl.edu.travelo.exception;

public class EmptyStringException extends RuntimeException {
    public EmptyStringException(String fieldName) {
        super(fieldName+ " can not be empty");
    }
}
