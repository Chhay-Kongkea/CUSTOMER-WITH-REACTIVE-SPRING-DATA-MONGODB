package kh.edu.istad.platform.customer.domain.entity;


import kh.edu.istad.common.domain.entity.AggregateRoot;
import kh.edu.istad.common.domain.valueobject.CustomerId;
import kh.edu.istad.platform.customer.domain.exception.CustomerDomainException;
import kh.edu.istad.platform.customer.domain.valueobject.CustomerStatus;
import kh.edu.istad.platform.customer.domain.valueobject.Email;
import kh.edu.istad.platform.customer.domain.valueobject.PhoneNumber;

import java.util.Objects;
import java.util.UUID;
import java.util.HashSet;
import java.util.Set;

public class Customer extends AggregateRoot<CustomerId> {


    private final String username;
    private String familyName;
    private String givenName;
    private final Email email;
    private final PhoneNumber phoneNumber;
    private CustomerStatus customerStatus;
    private final Set<UUID> segmentIds = new HashSet<>();

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
            throw new CustomerDomainException("Customer status must be null when initiating a customer");
        }
        if (username == null || username.isBlank()) {
            throw new CustomerDomainException("Username must not be blank");
        }
        if (familyName == null || familyName.isBlank()
                || givenName == null || givenName.isBlank()) {
            throw new CustomerDomainException("Family name and given name must not be blank");
        }
        if (email == null || phoneNumber == null) {
            throw new CustomerDomainException("Email and phone number are required");
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
        changeStatus(CustomerStatus.INACTIVE);
    }

    public void changeStatus(CustomerStatus status) {
        if (status == null) {
            throw new CustomerDomainException("Customer status is required");
        }
        if (customerStatus == status) {
            throw new CustomerDomainException("Customer is already " + status);
        }
        customerStatus = status;
    }

    public void assignSegment(UUID segmentId) {
        if (segmentId == null) throw new CustomerDomainException("Segment id is required");
        if (!segmentIds.add(segmentId)) throw new CustomerDomainException("Customer is already assigned to this segment");
    }

    public void removeSegment(UUID segmentId) {
        if (!segmentIds.remove(segmentId)) throw new CustomerDomainException("Customer is not assigned to this segment");
    }


    private Customer(Builder builder) {
        super.setId(builder.id);

        username = builder.username;
        familyName = builder.familyName;
        givenName = builder.givenName;
        email = builder.email;
        phoneNumber = builder.phoneNumber;
        customerStatus = builder.customerStatus;
        if (builder.segmentIds != null) segmentIds.addAll(builder.segmentIds);
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
        private Set<UUID> segmentIds;

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

        public Builder segmentIds(Set<UUID> val) { segmentIds = val; return this; }

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

    public Set<UUID> getSegmentIds() { return Set.copyOf(segmentIds); }



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
