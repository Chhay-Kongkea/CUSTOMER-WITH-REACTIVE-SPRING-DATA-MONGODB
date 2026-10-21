package kh.edu.istad.platform.customer.domain.service;

import kh.edu.istad.platform.customer.domain.entity.Customer;
import kh.edu.istad.platform.customer.domain.event.CustomerDeactivatedEvent;
import kh.edu.istad.platform.customer.domain.event.CustomerInitiatedEvent;
import kh.edu.istad.platform.customer.domain.event.CustomerUpdatedEvent;
import kh.edu.istad.platform.customer.domain.valueobject.CustomerStatus;

public interface CustomerDomainService {

    CustomerInitiatedEvent initiateCustomer(Customer customer);
    CustomerUpdatedEvent updateCustomer(Customer customer);
    CustomerUpdatedEvent changeStatus(Customer customer, CustomerStatus status);
    CustomerDeactivatedEvent deactivateCustomer(Customer customer);

}
