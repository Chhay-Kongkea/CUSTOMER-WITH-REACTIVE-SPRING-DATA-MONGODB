package kh.edu.istad.platform.customer.domain.usecase;
import kh.edu.istad.platform.customer.domain.dto.SegmentResult;
import kh.edu.istad.platform.customer.domain.port.out.CustomerSegmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
@Component @RequiredArgsConstructor
public class GetCustomerSegmentsUseCase {
    private final CustomerSegmentRepository repository;
    public Flux<SegmentResult> execute() {
        return repository.findAll().map(SegmentResult::new);
    }
}
