package kh.edu.istad.platform.customer.domain.usecase;

import kh.edu.istad.platform.customer.domain.dto.CustomerFilter;
import kh.edu.istad.platform.customer.domain.dto.CustomerPageResponse;
import kh.edu.istad.platform.customer.domain.dto.GetCustomerResult;
import kh.edu.istad.platform.customer.domain.mapper.CustomerMapper;
import kh.edu.istad.platform.customer.domain.port.out.CustomerRepository;
import kh.edu.istad.platform.customer.domain.service.CustomerDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetCustomersUseCase {


    private final CustomerDomainService customerDomainService;
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public Mono<CustomerPageResponse<GetCustomerResult>> execute(CustomerFilter customerFilter){
        return Mono.zip(
                        customerRepository.findByFilter(customerFilter).collectList(),
                        customerRepository.countByFilter(customerFilter))
                .map(result -> {
                    int page = customerFilter.normalizedPage();
                    int size = customerFilter.normalizedSize();
                    long total = result.getT2();
                    int pages = (int) Math.ceil((double) total / size);
                    return new CustomerPageResponse<>(result.getT1().stream()
                            .map(customerMapper::toCustomerResult).toList(), page, size, total, pages);
                });
    }

}
