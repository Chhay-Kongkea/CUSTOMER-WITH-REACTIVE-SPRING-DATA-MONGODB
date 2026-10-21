package kh.edu.istad.platform.customer.domain.event;

import kh.edu.istad.platform.customer.domain.entity.Customer;

public class CustomerGetAllEvent {

    private final Customer customer;


    public CustomerGetAllEvent(Customer customer) {
        this.customer = customer;
    }

   public Customer getCustomer(){
        return customer;
   }
}
