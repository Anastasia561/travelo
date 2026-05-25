package pl.edu.travelo.application.discount;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.travelo.application.discount.dto.DiscountResponseDto;
import pl.edu.travelo.common.wrapper.ResponseWrapper;

@RestController
@RequestMapping("/discounts")
public class DiscountController {
    private final DiscountService discountService;

    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

    @GetMapping("/{promeCode}")
    public ResponseWrapper<DiscountResponseDto> getRelevantByPromeCode(@PathVariable String promeCode) {
        return ResponseWrapper.ok(discountService.getByPromeCodeRelevant(promeCode, 1L));
    }
}
