package kh.edu.istad.platform.customer.domain.port.out;

import kh.edu.istad.common.domain.valueobject.CustomerId;
import kh.edu.istad.platform.customer.domain.dto.CustomerFilter;
import kh.edu.istad.platform.customer.domain.entity.Customer;
import kh.edu.istad.platform.customer.domain.dto.CustomerStatisticsResult;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface CustomerRepository {

    Mono<Customer> save(Customer customer);
    Flux<Customer> findAll();

    Flux<Customer> findByFilter(CustomerFilter filter);

    Mono<Long> countByFilter(CustomerFilter filter);
    Mono<CustomerStatisticsResult> statistics();

    Mono<Customer> findById(CustomerId id);
    Mono<Void> deleteById(CustomerId id);
    Mono<Customer> findByUsername(String username);
    Mono<Customer> findByEmail(String email);
    Mono<Customer> findByPhoneNumber(String phoneNumber);
    Flux<Customer> query();

}
