package pl.edu.travelo.application.customer.service;

import pl.edu.travelo.domain.model.Customer;

public interface CustomerService {
    Customer findById(long id);

    int getLoyaltyPoints(long id);
}
