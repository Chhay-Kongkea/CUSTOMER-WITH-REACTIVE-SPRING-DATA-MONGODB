package kh.edu.istad.platform.customer.restapi.controller;

import jakarta.validation.Valid;
import kh.edu.istad.platform.customer.domain.dto.CustomerFilter;
import kh.edu.istad.platform.customer.domain.dto.CustomerPageResponse;
import kh.edu.istad.platform.customer.domain.dto.CustomerStatisticsResult;
import kh.edu.istad.platform.customer.domain.usecase.*;
import kh.edu.istad.platform.customer.restapi.dto.*;
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
    private final GetCustomersUseCase getCustomersUseCase;
    private final GetCustomerUseCase getCustomerUseCase;
    private final UpdateCustomerUseCase updateCustomerUseCase;
    private final ChangeCustomerStatusUseCase changeCustomerStatusUseCase;
    private final DeleteCustomerUseCase deleteCustomerUseCase;
    private final ManageCustomerSegmentMembershipUseCase membershipUseCase;
    private final GetCustomerStatisticsUseCase statisticsUseCase;
    private final CustomerWebMapper customerWebMapper;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Mono<CustomerInitiateResponse> create(@Valid @RequestBody CreateCustomerRequest request) {
        return initiateCustomerUseCase.execute(customerWebMapper.toDomain(request))
                .map(customerWebMapper::toResponse);
    }

    @GetMapping
    public Mono<CustomerPageResponse<CustomerGetResponse>> getAll(@ModelAttribute CustomerFilter filter) {
        return getCustomersUseCase.execute(filter).map(
                page -> new CustomerPageResponse<>(page.content().stream()
                .map(customerWebMapper::toResponse).toList(), page.page(),
                        page.size(), page.totalElements(), page.totalPages()));
    }

    @GetMapping("/{id}")
    public Mono<CustomerGetResponse> getById(@PathVariable UUID id) {
        return getCustomerUseCase.execute(id).map(customerWebMapper::toResponse);
    }

    @PutMapping("/{id}")
    public Mono<CustomerUpdateResponse> update(@PathVariable UUID id, @Valid @RequestBody CustomerUpdateRequest request) {
        return updateCustomerUseCase.execute(id, customerWebMapper
                .toCommand(request)).map(customerWebMapper::toUpdateResponse);
    }

    @PatchMapping("/{id}/status")
    public Mono<CustomerDeactivateResponse> changeStatus(@PathVariable UUID id, @Valid @RequestBody ChangeCustomerStatusRequest request) {
        return changeCustomerStatusUseCase.execute(id, request.status())
                .map(customerWebMapper::toStatusResponse);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable UUID id) {

        return deleteCustomerUseCase.execute(id);
    }

    @PutMapping("/{customerId}/segments/{segmentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> assignSegment(@PathVariable UUID customerId, @PathVariable UUID segmentId) {
        return membershipUseCase.assign(customerId, segmentId);
    }

    @DeleteMapping("/{customerId}/segments/{segmentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> removeSegment(@PathVariable UUID customerId, @PathVariable UUID segmentId) {
        return membershipUseCase.remove(customerId, segmentId);
    }

    @GetMapping("/statistics")
    public Mono<CustomerStatisticsResult> statistics() {
        return statisticsUseCase.execute();
    }
}
