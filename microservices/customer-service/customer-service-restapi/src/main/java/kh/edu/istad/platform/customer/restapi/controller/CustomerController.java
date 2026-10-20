package kh.edu.istad.platform.customer.restapi.controller;

import jakarta.validation.Valid;



import kh.edu.istad.platform.customer.domain.usecase.InitiateCustomerUseCase;

import kh.edu.istad.platform.customer.restapi.dto.CreateCustomerRequest;
import kh.edu.istad.platform.customer.restapi.dto.CustomerInitiateResponse;
import kh.edu.istad.platform.customer.restapi.mapper.CustomerWebMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final InitiateCustomerUseCase initiateCustomerUseCase;
//    private final UpdateCustomerUseCase updateCustomerUseCase;
//    private final DeactivateCustomerUseCase deactivateCustomerUseCase;

    private final CustomerWebMapper customerWebMapper;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Mono<CustomerInitiateResponse> initiateCustomer(
            @Valid @RequestBody CreateCustomerRequest request) {

        return initiateCustomerUseCase.execute(
                        customerWebMapper.toDomain(request)
                )
                .map(customerWebMapper::toResponse);
    }



//    @GetMapping("/{customerId}")
//    public CustomerGetResponse getCustomer(
//            @PathVariable("customerId") UUID customerId) {
//
//        GetCustomerResult result = getCustomerUseCase.execute(customerId);
//        return customerWebMapper.toResponse(result);
//    }
//
//    @PutMapping("/{customerId}")
//    public CustomerUpdateResponse updateCustomer(
//            @PathVariable("customerId") UUID customerId,
//            @Valid @RequestBody CustomerUpdateRequest request) {
//
//        UpdateCustomerResult result = updateCustomerUseCase.execute(
//                customerId,
//                customerWebMapper.toCommand(request)
//        );
//
//        return customerWebMapper.toResponse(result);
//    }
//
//    @PatchMapping("/{customerId}/deactivate")
//    public CustomerDeactivateResponse deactivateCustomer(
//
//            @PathVariable("customerId") UUID customerId) {
//
//        DeactivateCustomerResult result = deactivateCustomerUseCase.execute(customerId);
//        return customerWebMapper.toResponse(result);
//    }
}
