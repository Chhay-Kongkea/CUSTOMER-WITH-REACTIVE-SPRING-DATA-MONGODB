
package kh.edu.istad.platform.customer.persistence.adapter;

import kh.edu.istad.platform.customer.domain.entity.Customer;
import kh.edu.istad.platform.customer.domain.port.out.CustomerRepository;
import kh.edu.istad.platform.customer.persistence.entity.CustomerEntity;
import kh.edu.istad.platform.customer.persistence.mapper.CustomerPersistenceMapper;
import kh.edu.istad.platform.customer.persistence.repository.CustomerJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CustomerRepositoryAdapter implements CustomerRepository {

    private final CustomerJpaRepository customerJpaRepository;
    private final CustomerPersistenceMapper customerPersistenceMapper;

    @Override
    public Customer save(Customer customer) {
        CustomerEntity entity =
                customerPersistenceMapper.toEntity(customer);

        CustomerEntity savedEntity =
                customerJpaRepository.save(entity);

        return customerPersistenceMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        return customerJpaRepository.findById(customerId)
                .map(customerPersistenceMapper::toDomain);
    }

    @Override
    public boolean existsByUsername(String username) {
        return customerJpaRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return customerJpaRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByPhoneNumber(String phoneNumber) {
        return customerJpaRepository.existsByPhoneNumber(phoneNumber);
    }
}
