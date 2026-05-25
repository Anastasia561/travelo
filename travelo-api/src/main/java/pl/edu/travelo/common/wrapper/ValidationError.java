package pl.edu.travelo.common.wrapper;

import java.time.OffsetDateTime;
import java.util.List;

public record ValidationError(
        String message,
        OffsetDateTime timestamp,
        List<FieldValidationError> validationErrors) implements ResponseError {
}
