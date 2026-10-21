package kh.edu.istad.platform.customer.domain.usecase;

import kh.edu.istad.platform.customer.domain.dto.CustomerStatisticsResult;
import kh.edu.istad.platform.customer.domain.port.out.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class GetCustomerStatisticsUseCase {
    private final CustomerRepository customerRepository;

    public Mono<CustomerStatisticsResult> execute() {

        return customerRepository.statistics();
    }
}
