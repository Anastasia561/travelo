package pl.edu.travelo.exception;

public class InvalidDiscountException extends RuntimeException {
    public InvalidDiscountException() {
        super("Discount Amount must be a decimal value between 0 and 1");
    }
}
