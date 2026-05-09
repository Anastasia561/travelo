package pl.edu.travelo.exception;

public class InvalidPhoneNumberFormatException extends RuntimeException {
    public InvalidPhoneNumberFormatException() {
        super("Phone Number format is invalid");
    }
}
