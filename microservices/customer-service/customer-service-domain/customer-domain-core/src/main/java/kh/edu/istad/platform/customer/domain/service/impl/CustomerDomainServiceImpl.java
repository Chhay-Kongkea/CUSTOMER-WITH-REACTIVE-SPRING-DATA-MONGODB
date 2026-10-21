package kh.edu.istad.platform.customer.domain.service.impl;

import kh.edu.istad.platform.customer.domain.entity.Customer;
import kh.edu.istad.platform.customer.domain.event.CustomerDeactivatedEvent;
import kh.edu.istad.platform.customer.domain.event.CustomerInitiatedEvent;
import kh.edu.istad.platform.customer.domain.event.CustomerUpdatedEvent;
import kh.edu.istad.platform.customer.domain.service.CustomerDomainService;
import kh.edu.istad.platform.customer.domain.valueobject.CustomerStatus;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class CustomerDomainServiceImpl implements CustomerDomainService {
    @Override
    public CustomerInitiatedEvent initiateCustomer(Customer customer) {
        customer.initiateCustomer();
        return new CustomerInitiatedEvent(customer, ZonedDateTime.now(ZoneOffset.UTC));
    }

    @Override
    public CustomerUpdatedEvent updateCustomer(Customer customer) {
        customer.updateCustomer(customer.getFamilyName(), customer.getGivenName());
        return new CustomerUpdatedEvent(customer, ZonedDateTime.now(ZoneOffset.UTC));
    }

    @Override
    public CustomerUpdatedEvent changeStatus(Customer customer, CustomerStatus status) {
        customer.changeStatus(status);
        return new CustomerUpdatedEvent(customer, ZonedDateTime.now(ZoneOffset.UTC));
    }

    @Override
    public CustomerDeactivatedEvent deactivateCustomer(Customer customer) {
        customer.deActivateCustomer();
        return new CustomerDeactivatedEvent(customer, ZonedDateTime.now(ZoneOffset.UTC));
    }
}
