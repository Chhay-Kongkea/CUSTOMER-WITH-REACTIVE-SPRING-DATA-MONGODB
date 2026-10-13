package kh.edu.istad.platform.customer.domain.dto;

import kh.edu.istad.platform.customer.domain.entity.Customer;

import java.util.UUID;

public record GetCustomerResult(
        UUID customerId,
        String username,
        String familyName,
        String givenName,
        String email,
        String phoneNumber,
        String customerStatus
) {

    public GetCustomerResult(Customer customer) {
        this(
                customer.getId().id(),
                customer.getUsername(),
                customer.getFamilyName(),
                customer.getGivenName(),
                customer.getEmail().value(),
                customer.getPhoneNumber().value(),
                customer.getCustomerStatus().name()
        );
    }
}
