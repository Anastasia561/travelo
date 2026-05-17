package pl.edu.travelo.discount.service;

import pl.edu.travelo.discount.dto.DiscountResponseDto;

import java.util.Set;

public interface DiscountService {
    boolean validateByPromeCode(String promeCode);

    Set<DiscountResponseDto> getRelevantDiscounts(int age);
}
