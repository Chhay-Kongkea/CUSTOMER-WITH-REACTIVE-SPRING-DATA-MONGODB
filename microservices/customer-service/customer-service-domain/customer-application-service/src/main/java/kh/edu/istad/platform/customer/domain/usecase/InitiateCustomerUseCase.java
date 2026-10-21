package kh.edu.istad.platform.customer.domain.usecase;

import kh.edu.istad.platform.customer.domain.dto.InitiateCustomerCommand;
import kh.edu.istad.platform.customer.domain.dto.InitiateCustomerResult;
import kh.edu.istad.platform.customer.domain.entity.Customer;
import kh.edu.istad.platform.customer.domain.exception.CustomerDomainException;
import kh.edu.istad.platform.customer.domain.port.out.CustomerRepository;
import kh.edu.istad.platform.customer.domain.service.CustomerDomainService;
import kh.edu.istad.platform.customer.domain.valueobject.Email;
import kh.edu.istad.platform.customer.domain.valueobject.PhoneNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class InitiateCustomerUseCase {

    private final CustomerDomainService customerDomainService;
    private final CustomerRepository customerRepository;

    public Mono<InitiateCustomerResult> execute(
            InitiateCustomerCommand command
    ) {
        Customer customer = Customer.newBuilder()
                .username(command.username())
                .familyName(command.familyName())
                .givenName(command.givenName())
                .email(new Email(command.email()))
                .phoneNumber(new PhoneNumber(command.phoneNumber()))
                .build();

        customerDomainService.initiateCustomer(customer);

        return Mono.zip(
                        customerRepository.findByUsername(command.username()).hasElement(),
                        customerRepository.findByEmail(customer.getEmail().value()).hasElement(),
                        customerRepository.findByPhoneNumber(customer.getPhoneNumber().value()).hasElement())
                .flatMap(existing -> {
                    if (existing.getT1()) return Mono.error(new CustomerDomainException("Username is already in use"));
                    if (existing.getT2()) return Mono.error(new CustomerDomainException("Email is already in use"));
                    if (existing.getT3()) return Mono.error(new CustomerDomainException("Phone number is already in use"));
                    return customerRepository.save(customer);
                })
                .map(savedCustomer ->
                        new InitiateCustomerResult(savedCustomer));
    }
}
