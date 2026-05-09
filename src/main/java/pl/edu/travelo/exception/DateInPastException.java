package pl.edu.travelo.exception;

public class DateInPastException extends RuntimeException {
    public DateInPastException(String fieldName) {
        super(fieldName + " can not be in the past");
    }
}
