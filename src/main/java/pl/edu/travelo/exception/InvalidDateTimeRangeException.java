package pl.edu.travelo.exception;

public class InvalidDateTimeRangeException extends RuntimeException {
    public InvalidDateTimeRangeException() {
        super("StartTime can not be bigger than EndTime");
    }
}
