package kh.edu.istad.platform.customer.domain.usecase;

import jakarta.transaction.Transactional;
import kh.edu.istad.platform.customer.domain.dto.UpdateCustomerCommand;
import kh.edu.istad.platform.customer.domain.dto.UpdateCustomerResult;
import kh.edu.istad.platform.customer.domain.entity.Customer;
import kh.edu.istad.platform.customer.domain.exception.CustomerDomainException;
import kh.edu.istad.platform.customer.domain.port.out.CustomerRepository;
import kh.edu.istad.platform.customer.domain.service.CustomerDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UpdateCustomerUseCase {

    private final CustomerRepository customerRepository;
    private final CustomerDomainService customerDomainService;

    @Transactional
    public UpdateCustomerResult execute(UUID customerId, UpdateCustomerCommand command) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerDomainException("Customer not found: " + customerId));

        customer.updateCustomer(command.familyName(), command.givenName());
        customerDomainService.updateCustomer(customer);

        return new UpdateCustomerResult(customerRepository.save(customer));
    }
}
