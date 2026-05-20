package pl.edu.travelo.application.discount.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.travelo.application.auth.core.CustomUserDetails;
import pl.edu.travelo.application.discount.dto.DiscountResponseDto;
import pl.edu.travelo.application.discount.service.DiscountService;
import pl.edu.travelo.wrapper.ResponseWrapper;

import java.util.Set;

@RestController
@RequestMapping("/discounts")
public class DiscountController {
    private final DiscountService discountService;

    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/validate")
    public ResponseWrapper<Boolean> validateDiscount(@RequestBody String promeCode) {
        return ResponseWrapper.ok(discountService.validateByPromeCode(promeCode));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/relevant")
    public ResponseWrapper<Set<DiscountResponseDto>> getRelevantDiscounts(
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return ResponseWrapper.ok(discountService.getRelevantDiscounts(customUserDetails.getId()));
    }
}
