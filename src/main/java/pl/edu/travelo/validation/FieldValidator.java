package pl.edu.travelo.validation;

import pl.edu.travelo.exception.DateInFutureException;
import pl.edu.travelo.exception.DateInPastException;
import pl.edu.travelo.exception.EmptyStringException;
import pl.edu.travelo.exception.InvalidDateTimeRangeException;
import pl.edu.travelo.exception.InvalidEmailFormatException;
import pl.edu.travelo.exception.InvalidPhoneNumberFormatException;
import pl.edu.travelo.exception.NegativeValueException;
import pl.edu.travelo.exception.NonPositiveValueException;
import pl.edu.travelo.exception.NullAttributeException;
import pl.edu.travelo.exception.RecursionException;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

public final class FieldValidator {

    public static String validateNullOrEmptyString(String value, String fieldName) {
        if (value == null) {
            throw new NullAttributeException(fieldName);
        }
        if (value.trim().isEmpty()) {
            throw new EmptyStringException(fieldName);
        }
        return value;
    }


    public static <T> T validateNonNegativeNumber(T value, String fieldName) {
        if (value instanceof Integer intValue) {
            if (intValue < 0) {
                throw new NegativeValueException(fieldName);
            }
            return value;
        } else if (value instanceof Long longValue) {
            if (longValue < 0) {
                throw new NegativeValueException(fieldName);
            }
            return value;
        } else if (value instanceof Double doubleValue) {
            if (doubleValue < 0) {
                throw new NegativeValueException(fieldName);
            }
            return value;
        } else {
            throw new IllegalArgumentException("Unsupported number type for validation");
        }
    }

    public static <T> T validatePositiveNumber(T value, String fieldName) {
        if (value instanceof Integer intValue) {
            if (intValue <= 0) {
                throw new NonPositiveValueException(fieldName);
            }
            return value;
        } else if (value instanceof Long longValue) {
            if (longValue <= 0) {
                throw new NonPositiveValueException(fieldName);
            }
            return value;
        } else if (value instanceof Double doubleValue) {
            if (doubleValue <= 0) {
                throw new NonPositiveValueException(fieldName);
            }
            return value;
        } else {
            throw new IllegalArgumentException("Unsupported number type for validation");
        }
    }

    public static LocalDate validateDateNotInTheFuture(LocalDate value, String fieldName) {
        if (value == null) {
            throw new NullAttributeException(fieldName);
        }

        if (value.isAfter(LocalDate.now())) {
            throw new DateInFutureException(fieldName);
        }
        return value;
    }

    public static LocalDate validateDateNotInThePast(LocalDate value, String fieldName) {
        if (value == null) {
            throw new NullAttributeException(fieldName);
        }

        if (value.isBefore(LocalDate.now())) {
            throw new DateInPastException(fieldName);
        }
        return value;
    }

    public static LocalDateTime validateDateTimeNotInThePast(LocalDateTime value, String fieldName) {
        if (value == null) {
            throw new NullAttributeException(fieldName);
        }

        if (value.isBefore(LocalDateTime.now())) {
            throw new DateInPastException(fieldName);
        }
        return value;
    }

    public static void validateDateTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime == null)
            throw new NullAttributeException("Start Time");

        if (endTime == null)
            throw new NullAttributeException("End Time");

        if (startTime.isAfter(endTime)) {
            throw new InvalidDateTimeRangeException();
        }
    }

    public static <T> T validateObjectNotNull(T value, String fieldName) {
        if (value == null) {
            throw new NullAttributeException(fieldName);
        }
        return value;
    }

    public static <T> T validateObjectRecursion(T passed, T passedTo) {
        if (passedTo.equals(passed)) {
            throw new RecursionException();
        }
        return passed;
    }

    public static Set<DayOfWeek> validateDayOfWeekList(Set<DayOfWeek> dayOfWeek, String fieldName) {
        validateObjectNotNull(dayOfWeek, fieldName);

        for (DayOfWeek day : dayOfWeek) {
            validateObjectNotNull(day, fieldName);
        }

        return dayOfWeek;
    }

//    /**
//     * validates seatRows > maxRow -> throw InvalidRowException
//     * validates seatNumber > rowWidth -> throw InvalidSeatNumberException
//     */
//    public static void validateSeatDimension(Seat seat, Hall hall) {
//        validateObjectNotNull(seat, "seat");
//        validateObjectNotNull(hall, "hall");
//
//        int maxRow = hall.getMaxRow();
//        int rowWidth = hall.getRowWidth();
//
//        if (seat.getRow() > maxRow) {
//            throw new InvalidRowException("Seat row exceeds hall max rows.");
//        }
//
//        if (seat.getSeatNumber() > rowWidth) {
//            throw new InvalidSeatNumberException("Seat number exceeds hall row width.");
//        }
//    }
//
//    public static void validateSeatNotDuplicate(Seat newSeat, Hall hall) {
//        for (Seat seat : hall.getSeats()) {
//            if (seat.getSeatNumber() == newSeat.getSeatNumber() && seat.getRow() == newSeat.getRow()) {
//                throw new DuplicateSeatException(
//                        "Seat with number " + newSeat.getSeatNumber() +
//                                " and row " + newSeat.getRow() + " already exists"
//                );
//            }
//        }
//    }

    public static String validateEmail(String email) {
        validateNullOrEmptyString(email, "Email");

        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

        if (!email.matches(emailRegex)) {
            throw new InvalidEmailFormatException();
        }

        return email;
    }

    public static String validatePhoneNumber(String phoneNumber) {
        if (phoneNumber == null) {
            return null;
        }

        if (phoneNumber.isBlank()) {
            throw new EmptyStringException("Phone Number");
        }

        String phoneRegex = "^\\+?[0-9\\s\\-()]{7,20}$";
        if (!phoneNumber.matches(phoneRegex)) {
            throw new InvalidPhoneNumberFormatException();
        }

        return phoneNumber;
    }
}