package kh.edu.istad.platform.customer.persistence.mapper;

import kh.edu.istad.platform.customer.domain.entity.Customer;
import kh.edu.istad.platform.customer.domain.valueobject.Email;
import kh.edu.istad.platform.customer.domain.valueobject.PhoneNumber;
import kh.edu.istad.platform.customer.persistence.entity.CustomerEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    Customer toCustomer(CustomerEntity customer);

    default Email map(String value) {
        if (value == null) {
            return null;
        }
        return new Email(value);
    }

    default PhoneNumber map1(String value){
        if(value == null){
            return null;
        }

        return new PhoneNumber(value);
    }
}