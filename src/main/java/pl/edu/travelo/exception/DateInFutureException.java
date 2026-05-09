package pl.edu.travelo.exception;

public class DateInFutureException extends RuntimeException {
    public DateInFutureException(String fieldName) {
        super(fieldName + " can not be in the future");
    }
}
