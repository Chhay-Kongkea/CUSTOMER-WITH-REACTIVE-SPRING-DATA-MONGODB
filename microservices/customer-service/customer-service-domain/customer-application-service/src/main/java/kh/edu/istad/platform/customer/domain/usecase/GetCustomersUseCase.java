package kh.edu.istad.platform.customer.domain.usecase;

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

    Flux<GetCustomerResult> getAll(){
        return customerRepository.findAll().map(customerMapper::toCustomerResult);
    }

}
