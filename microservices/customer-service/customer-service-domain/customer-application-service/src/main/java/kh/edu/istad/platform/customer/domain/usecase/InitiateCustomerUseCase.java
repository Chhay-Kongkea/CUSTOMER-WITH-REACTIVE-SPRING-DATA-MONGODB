
package kh.edu.istad.platform.customer.domain.usecase;

import jakarta.transaction.Transactional;
import kh.edu.istad.platform.customer.domain.dto.InitiateCustomerCommand;
import kh.edu.istad.platform.customer.domain.dto.InitiateCustomerResult;
import kh.edu.istad.platform.customer.domain.entity.Customer;
import kh.edu.istad.platform.customer.domain.event.CustomerDeactivatedEvent;
import kh.edu.istad.platform.customer.domain.event.CustomerUpdatedEvent;
import kh.edu.istad.platform.customer.domain.exception.CustomerDomainException;
import kh.edu.istad.platform.customer.domain.port.out.CustomerRepository;
import kh.edu.istad.platform.customer.domain.service.CustomerDomainService;
import kh.edu.istad.platform.customer.domain.valueobject.Email;
import kh.edu.istad.platform.customer.domain.valueobject.PhoneNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class InitiateCustomerUseCase {

    private final CustomerDomainService customerDomainService;
    private final CustomerRepository customerRepository;

    @Transactional
    public InitiateCustomerResult execute(
            InitiateCustomerCommand command
    ) {

        if (customerRepository.existsByUsername(command.username())) {
            throw new CustomerDomainException(
                    "Username already exists"
            );
        }


        if (customerRepository.existsByEmail(command.email())) {
            throw new CustomerDomainException(
                    "Email already exists"
            );
        }


        if (customerRepository.existsByPhoneNumber(command.phoneNumber())) {
            throw new CustomerDomainException(
                    "Phone number already exists"
            );
        }


        Customer customer = Customer.newBuilder()
                .username(command.username())
                .familyName(command.familyName())
                .givenName(command.givenName())
                .email(new Email(command.email()))
                .phoneNumber(new PhoneNumber(command.phoneNumber()))
                .build();


        customerDomainService.initiateCustomer(customer);


        Customer savedCustomer = customerRepository.save(customer);


        return new InitiateCustomerResult(savedCustomer);
    }


}
