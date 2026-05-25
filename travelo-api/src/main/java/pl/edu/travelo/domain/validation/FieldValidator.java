package pl.edu.travelo.domain.validation;

import pl.edu.travelo.domain.model.Seat;
import pl.edu.travelo.domain.model.Vehicle;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

public final class FieldValidator {

    public static String validateNullOrEmptyString(String value, String fieldName) {
        if (value == null) {
            throw new IllegalArgumentException(fieldName + " can not be null");
        }
        if (value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " can not be empty");
        }
        return value;
    }


    public static <T> T validateNonNegativeNumber(T value, String fieldName) {
        switch (value) {
            case Integer intValue -> {
                if (intValue < 0) {
                    throw new IllegalArgumentException(fieldName + " must be a non-negative value ( >= 0)");
                }
                return value;
            }
            case Long longValue -> {
                if (longValue < 0) {
                    throw new IllegalArgumentException(fieldName + " must be a non-negative value ( >= 0)");
                }
                return value;
            }
            case Double doubleValue -> {
                if (doubleValue < 0) {
                    throw new IllegalArgumentException(fieldName + " must be a non-negative value ( >= 0)");
                }
                return value;
            }
            case null, default -> throw new IllegalArgumentException("Unsupported number type for validation");
        }
    }

    public static <T> T validatePositiveNumber(T value, String fieldName) {
        switch (value) {
            case Integer intValue -> {
                if (intValue <= 0) {
                    throw new IllegalArgumentException(fieldName + " must be a positive value ( > 0)");
                }
                return value;
            }
            case Long longValue -> {
                if (longValue <= 0) {
                    throw new IllegalArgumentException(fieldName + " must be a positive value ( > 0)");
                }
                return value;
            }
            case Double doubleValue -> {
                if (doubleValue <= 0) {
                    throw new IllegalArgumentException(fieldName + " must be a positive value ( > 0)");
                }
                return value;
            }
            case null, default -> throw new IllegalArgumentException("Unsupported number type for validation");
        }
    }

    public static LocalDate validateDateNotInTheFuture(LocalDate value, String fieldName) {
        if (value == null) {
            throw new IllegalArgumentException(fieldName + " can not be null");
        }

        if (value.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(fieldName + " can not be in the future");
        }
        return value;
    }

    public static void validateDateTimeNotInThePast(LocalDateTime value, String fieldName) {
        if (value == null) {
            throw new IllegalArgumentException(fieldName + " can not be null");
        }

        if (value.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException(fieldName + " can not be in the past");
        }
    }

    public static void validateDateTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime == null)
            throw new IllegalArgumentException("Start Time can not be null");

        if (endTime == null)
            throw new IllegalArgumentException("End Time can not be null");

        if (startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("StartTime can not be bigger than EndTime");
        }
    }

    public static <T> T validateObjectNotNull(T value, String fieldName) {
        if (value == null) {
            throw new IllegalArgumentException(fieldName + " can not be null");
        }
        return value;
    }

    public static Set<DayOfWeek> validateDayOfWeekList(Set<DayOfWeek> dayOfWeek, String fieldName) {
        validateObjectNotNull(dayOfWeek, fieldName);

        for (DayOfWeek day : dayOfWeek) {
            validateObjectNotNull(day, fieldName);
        }

        return dayOfWeek;
    }

    public static void validateSeatDimension(Seat seat, Vehicle vehicle) {
        validateObjectNotNull(seat, "seat");
        validateObjectNotNull(vehicle, "vehicle");

        int maxRow = vehicle.getMaxRow();
        int rowWidth = vehicle.getRowWidth();

        if (seat.getRow() > maxRow) {
            throw new IllegalArgumentException("Seat row exceeds vehicle max rows");
        }

        if (seat.getSeatNumber() > rowWidth) {
            throw new IllegalArgumentException("Seat number exceeds vehicle row width");
        }
    }

    public static void validateSeatNotDuplicate(Seat newSeat, Vehicle vehicle) {
        for (Seat seat : vehicle.getSeats()) {
            if (seat.getSeatNumber() == newSeat.getSeatNumber() && seat.getRow() == newSeat.getRow()) {
                throw new IllegalArgumentException(
                        "Seat with number " + newSeat.getSeatNumber() +
                                " and row " + newSeat.getRow() + " already exists"
                );
            }
        }
    }

    public static String validateEmail(String email) {
        validateNullOrEmptyString(email, "Email");

        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

        if (!email.matches(emailRegex)) {
            throw new IllegalArgumentException("Email format is invalid");
        }

        return email;
    }

    public static String validatePhoneNumber(String phoneNumber) {
        if (phoneNumber == null) {
            return null;
        }

        if (phoneNumber.isBlank()) {
            throw new IllegalArgumentException("Phone number can not be empty");
        }

        String phoneRegex = "^\\+?[0-9\\s\\-()]{7,20}$";
        if (!phoneNumber.matches(phoneRegex)) {
            throw new IllegalArgumentException("Phone Number format is invalid");
        }

        return phoneNumber;
    }
}