package kh.edu.istad.platform.customer.domain.usecase;
import kh.edu.istad.platform.customer.domain.dto.*;
import kh.edu.istad.platform.customer.domain.exception.CustomerDomainException;
import kh.edu.istad.platform.customer.domain.port.out.CustomerSegmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import java.util.UUID;
@Component @RequiredArgsConstructor
public class UpdateCustomerSegmentUseCase {
    private final CustomerSegmentRepository repository;


    public Mono<SegmentResult> execute(UUID id, SegmentCommand command) {

        return repository.findById(id).switchIfEmpty
                        (Mono.error(new CustomerDomainException("Customer segment not found: " + id)))
                         .flatMap(segment -> repository.findByName(command.name())
                         .filter(other -> !other.getId().equals(id))
                         .flatMap(other -> Mono.<SegmentResult>error(new CustomerDomainException("Segment name is already in use")))
                         .switchIfEmpty(Mono.defer(() -> { segment.update
                         (command.name(), command.description()); return repository.save(segment).map(SegmentResult::new); })));
    }
}
