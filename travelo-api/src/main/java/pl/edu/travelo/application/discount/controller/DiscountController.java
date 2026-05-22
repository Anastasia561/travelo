package pl.edu.travelo.application.discount.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.travelo.application.auth.core.CustomUserDetails;
import pl.edu.travelo.application.discount.dto.DiscountResponseDto;
import pl.edu.travelo.application.discount.service.DiscountService;
import pl.edu.travelo.wrapper.ResponseWrapper;

@RestController
@RequestMapping("/discounts")
public class DiscountController {
    private final DiscountService discountService;

    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{promeCode}")
    public ResponseWrapper<DiscountResponseDto> getRelevantByPromeCode(@PathVariable String promeCode,
                                                                       @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return ResponseWrapper.ok(discountService.getByPromeCodeRelevant(promeCode, customUserDetails.getId()));
    }
}
