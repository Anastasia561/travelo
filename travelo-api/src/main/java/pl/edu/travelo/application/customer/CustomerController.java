package pl.edu.travelo.application.customer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.travelo.common.wrapper.ResponseWrapper;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/points")
    public ResponseWrapper<Integer> getPoints() {
        return ResponseWrapper.ok(customerService.getLoyaltyPoints(1L));
    }
}
