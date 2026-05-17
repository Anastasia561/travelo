package pl.edu.travelo.application.customer.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.travelo.application.auth.core.CustomUserDetails;
import pl.edu.travelo.application.customer.service.CustomerService;
import pl.edu.travelo.wrapper.ResponseWrapper;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/points")
    public ResponseWrapper<Integer> getPoints(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return ResponseWrapper.ok(customerService.getLoyaltyPoints(customUserDetails.getId()));
    }
}
