package kh.edu.istad.platform.customer.restapi.mapper;

import ch.qos.logback.core.model.ComponentModel;
import kh.edu.istad.platform.customer.domain.dto.InitiateCustomerCommand;
import kh.edu.istad.platform.customer.domain.dto.InitiateCustomerResult;
import kh.edu.istad.platform.customer.restapi.dto.CustomerInitiateRequest;
import kh.edu.istad.platform.customer.restapi.dto.CustomerInitiateResponse;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerWebMapper {
       InitiateCustomerCommand toCommand(CustomerInitiateRequest request);
       CustomerInitiateResponse toResponse(InitiateCustomerResult result);
}
