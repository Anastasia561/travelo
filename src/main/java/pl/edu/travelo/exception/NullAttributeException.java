package pl.edu.travelo.exception;

public class NullAttributeException extends RuntimeException {
    public NullAttributeException(String fieldName) {
        super(fieldName + " can not be null");
    }
}
