package kh.edu.istad.platform.customer.domain.usecase;

import kh.edu.istad.common.domain.valueobject.CustomerId;
import kh.edu.istad.platform.customer.domain.dto.GetCustomerResult;
import kh.edu.istad.platform.customer.domain.exception.CustomerDomainException;
import kh.edu.istad.platform.customer.domain.port.out.CustomerRepository;
import kh.edu.istad.platform.customer.domain.service.CustomerDomainService;
import kh.edu.istad.platform.customer.domain.valueobject.CustomerStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ChangeCustomerStatusUseCase {
    private final CustomerRepository customerRepository;
    private final CustomerDomainService customerDomainService;

    public Mono<GetCustomerResult> execute(UUID id, CustomerStatus status) {
        return customerRepository.findById(new CustomerId(id))
                .switchIfEmpty(Mono.error(new CustomerDomainException("Customer not found: " + id)))
                .doOnNext(customer -> customerDomainService.changeStatus(customer, status))
                .flatMap(customerRepository::save)
                .map(GetCustomerResult::new);
    }
}
