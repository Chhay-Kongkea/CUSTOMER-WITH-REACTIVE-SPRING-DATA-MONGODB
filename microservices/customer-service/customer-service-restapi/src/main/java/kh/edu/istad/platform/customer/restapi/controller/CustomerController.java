package kh.edu.istad.platform.customer.restapi.controller;

import jakarta.validation.Valid;
import kh.edu.istad.platform.customer.domain.dto.DeactivateCustomerResult;
import kh.edu.istad.platform.customer.domain.dto.GetCustomerResult;
import kh.edu.istad.platform.customer.domain.dto.InitiateCustomerResult;
import kh.edu.istad.platform.customer.domain.dto.UpdateCustomerResult;
import kh.edu.istad.platform.customer.domain.usecase.DeactivateCustomerUseCase;
import kh.edu.istad.platform.customer.domain.usecase.GetCustomerUseCase;
import kh.edu.istad.platform.customer.domain.usecase.InitiateCustomerUseCase;
import kh.edu.istad.platform.customer.domain.usecase.UpdateCustomerUseCase;
import kh.edu.istad.platform.customer.restapi.dto.CustomerInitiateRequest;
import kh.edu.istad.platform.customer.restapi.dto.CustomerInitiateResponse;
import kh.edu.istad.platform.customer.restapi.dto.CustomerDeactivateResponse;
import kh.edu.istad.platform.customer.restapi.dto.CustomerGetResponse;
import kh.edu.istad.platform.customer.restapi.dto.CustomerUpdateRequest;
import kh.edu.istad.platform.customer.restapi.dto.CustomerUpdateResponse;
import kh.edu.istad.platform.customer.restapi.mapper.CustomerWebMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final InitiateCustomerUseCase initiateCustomerUseCase;
    private final UpdateCustomerUseCase updateCustomerUseCase;
    private final DeactivateCustomerUseCase deactivateCustomerUseCase;
    private final GetCustomerUseCase getCustomerUseCase;
    private final CustomerWebMapper customerWebMapper;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CustomerInitiateResponse initiateCustomer(
            @Valid @RequestBody CustomerInitiateRequest request) {

        InitiateCustomerResult result =
                initiateCustomerUseCase.execute(
                        customerWebMapper.toCommand(request)
                );

        return customerWebMapper.toResponse(result);
    }

    @GetMapping("/{customerId}")
    public CustomerGetResponse getCustomer(
            @PathVariable("customerId") UUID customerId) {

        GetCustomerResult result = getCustomerUseCase.execute(customerId);
        return customerWebMapper.toResponse(result);
    }

    @PutMapping("/{customerId}")
    public CustomerUpdateResponse updateCustomer(
            @PathVariable("customerId") UUID customerId,
            @Valid @RequestBody CustomerUpdateRequest request) {

        UpdateCustomerResult result = updateCustomerUseCase.execute(
                customerId,
                customerWebMapper.toCommand(request)
        );

        return customerWebMapper.toResponse(result);
    }

    @PatchMapping("/{customerId}/deactivate")
    public CustomerDeactivateResponse deactivateCustomer(

            @PathVariable("customerId") UUID customerId) {

        DeactivateCustomerResult result = deactivateCustomerUseCase.execute(customerId);
        return customerWebMapper.toResponse(result);
    }
}
