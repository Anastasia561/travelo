package pl.edu.travelo.application.discount.service;

import pl.edu.travelo.application.discount.dto.DiscountResponseDto;

import java.util.Set;

public interface DiscountService {
    boolean validateByPromeCode(String promeCode);

    Set<DiscountResponseDto> getRelevantDiscounts(long customerId);
}
