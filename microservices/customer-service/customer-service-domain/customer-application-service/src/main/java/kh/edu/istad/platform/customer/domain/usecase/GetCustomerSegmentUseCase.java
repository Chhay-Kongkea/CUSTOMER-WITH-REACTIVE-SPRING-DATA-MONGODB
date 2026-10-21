package kh.edu.istad.platform.customer.domain.usecase;
import kh.edu.istad.platform.customer.domain.dto.SegmentResult;
import kh.edu.istad.platform.customer.domain.exception.CustomerDomainException;
import kh.edu.istad.platform.customer.domain.port.out.CustomerSegmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import java.util.UUID;
@Component @RequiredArgsConstructor
public class GetCustomerSegmentUseCase {
    private final CustomerSegmentRepository repository;
    public Mono<SegmentResult> execute(UUID id) {
        return repository.findById(id).switchIfEmpty
                (Mono.error(new CustomerDomainException("Customer segment not found: " + id)))
                .map(SegmentResult::new); }
}
