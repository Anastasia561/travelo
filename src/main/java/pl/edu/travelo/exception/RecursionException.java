package pl.edu.travelo.exception;

public class RecursionException extends RuntimeException {
    public RecursionException() {
        super("An object cannot have a recursive association with itself");
    }
}
