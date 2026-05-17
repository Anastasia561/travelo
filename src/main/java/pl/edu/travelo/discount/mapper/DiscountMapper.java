package pl.edu.travelo.discount.mapper;

import org.springframework.stereotype.Component;
import pl.edu.travelo.discount.dto.DiscountResponseDto;
import pl.edu.travelo.domain.enums.DiscountType;
import pl.edu.travelo.domain.model.LimitedDiscount;
import pl.edu.travelo.domain.model.RegularDiscount;

@Component
public class DiscountMapper {

    public DiscountResponseDto toDtoFromLimited(LimitedDiscount discount) {
        if (discount.getDiscountType() == DiscountType.AGE_GROUP_DISCOUNT) {
            return new DiscountResponseDto(
                    discount.getPromoCode(),
                    discount.getDiscountAmount(),
                    discount.getAgeGroup(),
                    null,
                    discount.getStartTime(),
                    discount.getEndTime(),
                    null
            );
        } else {
            return new DiscountResponseDto(
                    discount.getPromoCode(),
                    discount.getDiscountAmount(),
                    null,
                    discount.getConditionDescription(),
                    discount.getStartTime(),
                    discount.getEndTime(),
                    null
            );
        }
    }

    public DiscountResponseDto toDtoFromRegular(RegularDiscount discount) {
        if (discount.getDiscountType() == DiscountType.AGE_GROUP_DISCOUNT) {
            return new DiscountResponseDto(
                    discount.getPromoCode(),
                    discount.getDiscountAmount(),
                    discount.getAgeGroup(),
                    null,
                    null,
                    null,
                    discount.getDayOfWeek()
            );
        } else {
            return new DiscountResponseDto(
                    discount.getPromoCode(),
                    discount.getDiscountAmount(),
                    null,
                    discount.getConditionDescription(),
                    null,
                    null,
                    discount.getDayOfWeek()
            );
        }
    }
}
