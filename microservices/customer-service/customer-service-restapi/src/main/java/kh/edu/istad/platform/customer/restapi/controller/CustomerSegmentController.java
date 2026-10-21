package kh.edu.istad.platform.customer.restapi.controller;

import jakarta.validation.Valid;
import kh.edu.istad.platform.customer.domain.dto.SegmentCommand;
import kh.edu.istad.platform.customer.domain.dto.SegmentResult;
import kh.edu.istad.platform.customer.domain.usecase.*;
import kh.edu.istad.platform.customer.restapi.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import java.util.UUID;

@RestController @RequiredArgsConstructor
@RequestMapping("/api/v1/customer-segments")
public class CustomerSegmentController {
    private final CreateCustomerSegmentUseCase createUseCase;
    private final GetCustomerSegmentsUseCase getAllUseCase;
    private final GetCustomerSegmentUseCase getUseCase;
    private final UpdateCustomerSegmentUseCase updateUseCase;
    private final DeleteCustomerSegmentUseCase deleteUseCase;

    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public Mono<CustomerSegmentResponse> create(@Valid @RequestBody CustomerSegmentRequest request) {
        return createUseCase.execute(new SegmentCommand(request.name(),
                request.description())).map(this::response);
    }
    @GetMapping
    public reactor.core.publisher.Flux<CustomerSegmentResponse> getAll() {
        return getAllUseCase.execute().map(this::response);

    }
    @GetMapping("/{id}")
    public Mono<CustomerSegmentResponse> get(@PathVariable UUID id) {
        return getUseCase.execute(id).map(this::response);
    }

    @PutMapping("/{id}")
    public Mono<CustomerSegmentResponse> update(@PathVariable UUID id,
                                                @Valid @RequestBody CustomerSegmentRequest request) {
        return updateUseCase.execute(id, new SegmentCommand(request.name(), request.description()))
                .map(this::response);

    }

    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable UUID id) {
        return deleteUseCase.execute(id);
    }

    private CustomerSegmentResponse response(
            SegmentResult result) {
        return new CustomerSegmentResponse(result.id(),
                result.name(), result.description()); }
}
