package pl.edu.travelo.discount.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.travelo.discount.dto.DiscountResponseDto;
import pl.edu.travelo.discount.service.DiscountService;
import pl.edu.travelo.wrapper.ResponseWrapper;

import java.util.Set;

@RestController
@RequestMapping("/discounts")
public class DiscountController {
    private final DiscountService discountService;

    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

    @PostMapping("/validate")
    public ResponseWrapper<Boolean> validateDiscount(@RequestBody String promeCode) {
        return ResponseWrapper.ok(discountService.validateByPromeCode(promeCode));
    }

    @GetMapping("/relevant")
    public ResponseWrapper<Set<DiscountResponseDto>> getRelevantDiscounts() {
        return ResponseWrapper.ok(discountService.getRelevantDiscounts(10));
    }
}
