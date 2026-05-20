package pl.edu.travelo.application.customer.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import pl.edu.travelo.application.customer.repository.CustomerRepository;
import pl.edu.travelo.domain.model.Customer;

@Service
class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer findById(long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));
    }

    @Override
    public int getLoyaltyPoints(long id) {
        return customerRepository.findById(id).map(Customer::getLoyaltyPoints)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));
    }
}
