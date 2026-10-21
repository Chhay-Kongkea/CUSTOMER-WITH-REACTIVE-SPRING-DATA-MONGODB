package kh.edu.istad.platform.customer.persistence.adapter;

import kh.edu.istad.platform.customer.domain.entity.CustomerSegment;
import kh.edu.istad.platform.customer.domain.port.out.CustomerSegmentRepository;
import kh.edu.istad.platform.customer.persistence.document.CustomerSegmentDocument;
import kh.edu.istad.platform.customer.persistence.repository.CustomerSegmentMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CustomerSegmentRepositoryAdapter implements CustomerSegmentRepository {
    private final CustomerSegmentMongoRepository repository;

    public Mono<CustomerSegment> save(CustomerSegment segment) {
        return repository.save(CustomerSegmentDocument.builder().id(segment.getId().toString())
                .name(segment.getName()).description(segment.getDescription()).build()).map(this::toDomain);
    }
    public Flux<CustomerSegment> findAll() { return repository.findAll().map(this::toDomain); }
    public Mono<CustomerSegment> findById(UUID id) { return repository.findById(id.toString()).map(this::toDomain); }
    public Mono<CustomerSegment> findByName(String name) { return repository.findByNameIgnoreCase(name).map(this::toDomain); }
    public Mono<Void> deleteById(UUID id) { return repository.deleteById(id.toString()).then(); }
    private CustomerSegment toDomain(CustomerSegmentDocument d) { return CustomerSegment.restore(UUID.fromString(d.getId()), d.getName(), d.getDescription()); }
}
