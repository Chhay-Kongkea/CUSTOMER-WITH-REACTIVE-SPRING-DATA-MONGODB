package kh.edu.istad.platform.customer.domain.usecase;

import jakarta.transaction.Transactional;
import kh.edu.istad.platform.customer.domain.dto.DeactivateCustomerResult;
import kh.edu.istad.platform.customer.domain.entity.Customer;
import kh.edu.istad.platform.customer.domain.exception.CustomerDomainException;
import kh.edu.istad.platform.customer.domain.port.out.CustomerRepository;
import kh.edu.istad.platform.customer.domain.service.CustomerDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DeactivateCustomerUseCase {

    private final CustomerRepository customerRepository;
    private final CustomerDomainService customerDomainService;

    @Transactional
    public DeactivateCustomerResult execute(UUID customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerDomainException("Customer not found: " + customerId));

        customerDomainService.deactivateCustomer(customer);

        return new DeactivateCustomerResult(customerRepository.save(customer));
    }
}
