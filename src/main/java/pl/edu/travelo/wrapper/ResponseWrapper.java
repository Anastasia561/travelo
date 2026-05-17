package pl.edu.travelo.wrapper;

import org.springframework.http.HttpStatus;

import java.time.OffsetDateTime;
import java.util.List;

public class ResponseWrapper<T> {
    private T data;
    private ResponseError error;
    private String status;
    private int statusCode;

    public void setData(T data) {
        this.data = data;
    }

    public void setError(ResponseError error) {
        this.error = error;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public T getData() {
        return data;
    }

    public ResponseError getError() {
        return error;
    }

    public String getStatus() {
        return status;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public static <T> ResponseWrapper<T> ok(T data) {
        ResponseWrapper<T> wrapper = new ResponseWrapper<>();
        wrapper.setData(data);
        wrapper.setStatus(HttpStatus.OK.name());
        wrapper.setStatusCode(HttpStatus.OK.value());
        return wrapper;
    }

    public static <T> ResponseWrapper<T> withError(HttpStatus status, String message) {
        ResponseWrapper<T> wrapper = new ResponseWrapper<>();
        wrapper.setError(new GeneralError(message, OffsetDateTime.now()));
        wrapper.setStatus(status.name());
        wrapper.setStatusCode(status.value());
        return wrapper;
    }

    public static <T> ResponseWrapper<T> withValidationError(HttpStatus status, String message,
                                                             List<FieldValidationError> validationErrors) {
        ResponseWrapper<T> wrapper = new ResponseWrapper<>();
        wrapper.setError(new ValidationError(message, OffsetDateTime.now(), validationErrors));
        wrapper.setStatus(status.name());
        wrapper.setStatusCode(status.value());
        return wrapper;
    }

    public static <T> ResponseWrapper<T> withStatus(HttpStatus status, T data) {
        ResponseWrapper<T> wrapper = new ResponseWrapper<>();
        wrapper.setData(data);
        wrapper.setStatus(status.name());
        wrapper.setStatusCode(status.value());
        return wrapper;
    }
}
