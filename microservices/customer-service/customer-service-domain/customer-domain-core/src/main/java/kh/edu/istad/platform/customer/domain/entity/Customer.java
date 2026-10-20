package kh.edu.istad.platform.customer.domain.entity;


import kh.edu.istad.common.domain.entity.AggregateRoot;
import kh.edu.istad.common.domain.valueobject.CustomerId;
import kh.edu.istad.platform.customer.domain.exception.CustomerDomainException;
import kh.edu.istad.platform.customer.domain.valueobject.CustomerStatus;
import kh.edu.istad.platform.customer.domain.valueobject.Email;
import kh.edu.istad.platform.customer.domain.valueobject.PhoneNumber;

import java.util.Objects;
import java.util.UUID;

public class Customer extends AggregateRoot<CustomerId> {


    private final String username;
    private String familyName;
    private String givenName;
    private final Email email;
    private final PhoneNumber phoneNumber;
    private CustomerStatus customerStatus;

    public void initiateCustomer(){
        validateCustomer();
        super.setId(new CustomerId(UUID.randomUUID()));
        customerStatus = CustomerStatus.ACTIVE;
    }

    private void validateCustomer(){
        if(super.getId() != null){
            throw new CustomerDomainException("Customer Id Must Be Null");
        }
        if(customerStatus != null){
            throw new CustomerDomainException("Customer Id Must Be Null");
        }
    }


    public void updateCustomer(String familyName, String givenName){
        if(familyName == null || familyName.isBlank()
                || givenName == null || givenName.isBlank()){
            throw new CustomerDomainException("Family name and given name must not be blank");
        }

        this.familyName = familyName;
        this.givenName = givenName;
    }


    public void deActivateCustomer(){
        if(customerStatus != CustomerStatus.ACTIVE){
            throw new CustomerDomainException("Can't deactivate, because customer status is not activate");
        }

        customerStatus = CustomerStatus.INACTIVE;
    }


    private Customer(Builder builder) {
        super.setId(builder.id);

        username = builder.username;
        familyName = builder.familyName;
        givenName = builder.givenName;
        email = builder.email;
        phoneNumber = builder.phoneNumber;
        customerStatus = builder.customerStatus;
    }

    public static Builder newBuilder() {
        return new Builder();
    }


    public static final class Builder {
        private CustomerId id;
        private String username;
        private String familyName;
        private String givenName;
        private Email email;
        private PhoneNumber phoneNumber;
        private CustomerStatus customerStatus;

        private Builder() {
        }

        public Builder id(CustomerId val) {
            id = val;
            return this;
        }

        public Builder username(String val) {
            username = val;
            return this;
        }

        public Builder familyName(String val) {
            familyName = val;
            return this;
        }

        public Builder givenName(String val) {
            givenName = val;
            return this;
        }

        public Builder email(Email val) {
            email = val;
            return this;
        }

        public Builder phoneNumber(PhoneNumber val) {
            phoneNumber = val;
            return this;
        }

        public Builder customerStatus(CustomerStatus val) {
            customerStatus = val;
            return this;
        }

        public Customer build() {
            return new Customer(this);
        }
    }

    public String getUsername() {
        return username;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getGivenName() {
        return givenName;
    }

    public Email getEmail() {
        return email;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public CustomerStatus getCustomerStatus() {
        return customerStatus;
    }



    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        Customer customer = (Customer) object;
        return Objects.equals(username, customer.username) && Objects.equals(familyName, customer.familyName) && Objects.equals(givenName, customer.givenName) && Objects.equals(email, customer.email) && Objects.equals(phoneNumber, customer.phoneNumber) && customerStatus == customer.customerStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), username, familyName, givenName, email, phoneNumber, customerStatus);
    }
}
