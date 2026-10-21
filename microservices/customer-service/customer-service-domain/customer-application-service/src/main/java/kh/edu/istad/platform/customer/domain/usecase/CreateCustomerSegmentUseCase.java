package kh.edu.istad.platform.customer.domain.usecase;
import kh.edu.istad.platform.customer.domain.dto.*;
import kh.edu.istad.platform.customer.domain.entity.CustomerSegment;
import kh.edu.istad.platform.customer.domain.exception.CustomerDomainException;
import kh.edu.istad.platform.customer.domain.port.out.CustomerSegmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
@Component @RequiredArgsConstructor
public class CreateCustomerSegmentUseCase {
    private final CustomerSegmentRepository repository;
    public Mono<SegmentResult> execute(SegmentCommand command) {
        return repository.findByName(command.name()).flatMap(x ->
                        Mono.<SegmentResult>error(new CustomerDomainException("Segment name is already in use")))
                            .switchIfEmpty(Mono.defer(() -> repository.save(CustomerSegment
                            .create(command.name(), command.description())).map(SegmentResult::new)));
    }
}
