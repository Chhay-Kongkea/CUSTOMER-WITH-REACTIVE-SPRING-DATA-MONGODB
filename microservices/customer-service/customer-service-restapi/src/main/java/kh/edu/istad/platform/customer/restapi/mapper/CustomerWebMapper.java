package kh.edu.istad.platform.customer.restapi.mapper;

import jakarta.validation.Valid;
import kh.edu.istad.platform.customer.domain.dto.InitiateCustomerCommand;
import kh.edu.istad.platform.customer.domain.valueobject.Email;
import kh.edu.istad.platform.customer.domain.valueobject.PhoneNumber;
import kh.edu.istad.platform.customer.domain.dto.InitiateCustomerResult;
import kh.edu.istad.platform.customer.domain.dto.GetCustomerResult;
import kh.edu.istad.platform.customer.domain.dto.UpdateCustomerCommand;
import kh.edu.istad.platform.customer.restapi.dto.CreateCustomerRequest;
import kh.edu.istad.platform.customer.restapi.dto.CustomerInitiateResponse;
import kh.edu.istad.platform.customer.restapi.dto.CustomerGetResponse;
import kh.edu.istad.platform.customer.restapi.dto.CustomerUpdateResponse;
import kh.edu.istad.platform.customer.restapi.dto.CustomerDeactivateResponse;
import kh.edu.istad.platform.customer.restapi.dto.CustomerUpdateRequest;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerWebMapper {

    InitiateCustomerCommand toDomain(@Valid CreateCustomerRequest request);

    CustomerInitiateResponse toResponse(InitiateCustomerResult result);
    CustomerGetResponse toResponse(GetCustomerResult result);
    CustomerUpdateResponse toUpdateResponse(GetCustomerResult result);
    CustomerDeactivateResponse toStatusResponse(GetCustomerResult result);
    UpdateCustomerCommand toCommand(CustomerUpdateRequest request);

    default Email toEmail(String email) {
        return new Email(email);
    }

    default PhoneNumber toPhoneNumber(String phoneNumber) {
        return new PhoneNumber(phoneNumber);
    }
}
