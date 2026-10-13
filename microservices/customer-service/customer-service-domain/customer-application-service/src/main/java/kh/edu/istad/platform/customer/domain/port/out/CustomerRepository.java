package kh.edu.istad.platform.customer.domain.port.out;

import kh.edu.istad.platform.customer.domain.entity.Customer;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {

    Customer save(Customer customer);
    Optional<Customer> findById(UUID customerId);
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

}
