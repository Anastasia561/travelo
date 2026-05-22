package pl.edu.travelo.application.discount.mapper;

import org.springframework.stereotype.Component;
import pl.edu.travelo.application.discount.dto.DiscountResponseDto;
import pl.edu.travelo.domain.model.Discount;

@Component
public class DiscountMapper {

    public DiscountResponseDto toDto(Discount discount) {
        return new DiscountResponseDto(discount.getPromoCode(), discount.getDiscountAmount());
    }
}
