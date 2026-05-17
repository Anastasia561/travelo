package pl.edu.travelo.discount.dto;

import pl.edu.travelo.domain.enums.AgeGroup;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Set;

public record DiscountResponseDto(
        String promeCode,
        double amount,
        AgeGroup ageGroup,
        String conditionDescription,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Set<DayOfWeek> days
) {
}
