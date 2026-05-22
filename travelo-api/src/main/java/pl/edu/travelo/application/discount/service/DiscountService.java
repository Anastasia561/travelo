package pl.edu.travelo.application.discount.service;

import pl.edu.travelo.application.discount.dto.DiscountResponseDto;
import pl.edu.travelo.domain.model.Discount;

public interface DiscountService {
    DiscountResponseDto getByPromeCodeRelevant(String promeCode, long customerId);

    Discount findByPromeCode(String promeCode);
}
