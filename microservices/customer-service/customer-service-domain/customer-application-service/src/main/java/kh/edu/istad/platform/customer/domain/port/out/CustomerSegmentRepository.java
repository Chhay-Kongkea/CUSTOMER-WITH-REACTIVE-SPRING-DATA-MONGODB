package kh.edu.istad.platform.customer.domain.port.out;

import kh.edu.istad.platform.customer.domain.entity.CustomerSegment;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

public interface CustomerSegmentRepository {
    Mono<CustomerSegment> save(CustomerSegment segment);
    Flux<CustomerSegment> findAll();
    Mono<CustomerSegment> findById(UUID id);
    Mono<CustomerSegment> findByName(String name);
    Mono<Void> deleteById(UUID id);
}
