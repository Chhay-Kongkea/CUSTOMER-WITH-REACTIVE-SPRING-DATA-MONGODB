package kh.edu.istad.platform.customer.persistence.mapper;

import kh.edu.istad.platform.customer.domain.entity.Customer;
import kh.edu.istad.platform.customer.domain.valueobject.Email;
import kh.edu.istad.platform.customer.domain.valueobject.PhoneNumber;
import kh.edu.istad.platform.customer.persistence.entity.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerPersistenceMapper {

    @Mapping(target = "customerId", source = "id.id")
    CustomerEntity toEntity(Customer customer);

    @Mapping(target = "id.id", source = "customerId")
    Customer toDomain(CustomerEntity entity);

    // Email -> String
    default String map(Email email) {
        return email == null ? null : email.value();
    }

    // String -> Email
    default Email map(String email) {
        return email == null ? null : new Email(email);
    }

    // PhoneNumber -> String
    default String map(PhoneNumber phoneNumber) {
        return phoneNumber == null ? null : phoneNumber.value();
    }

    // String -> PhoneNumber
    default PhoneNumber mapPhoneNumber(String phoneNumber) {
        return phoneNumber == null ? null : new PhoneNumber(phoneNumber);
    }
}
