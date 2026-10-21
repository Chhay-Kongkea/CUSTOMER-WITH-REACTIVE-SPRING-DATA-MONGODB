package kh.edu.istad.platform.customer.domain.usecase;
import kh.edu.istad.common.domain.valueobject.CustomerId;
import kh.edu.istad.platform.customer.domain.exception.CustomerDomainException;
import kh.edu.istad.platform.customer.domain.port.out.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import java.util.UUID;
@Component @RequiredArgsConstructor
public class ManageCustomerSegmentMembershipUseCase {
    private final CustomerRepository customerRepository;
    private final CustomerSegmentRepository segmentRepository;

    public Mono<Void> assign(UUID customerId, UUID segmentId) {
        return change(customerId, segmentId, true);
    }
    public Mono<Void> remove(UUID customerId, UUID segmentId) {
        return change(customerId, segmentId, false);
    }
    private Mono<Void> change(UUID customerId, UUID segmentId, boolean assign) {
        return Mono.zip(customerRepository.findById(new CustomerId(customerId))
                                .switchIfEmpty(Mono.error(new CustomerDomainException("Customer not found: " + customerId))),
                                 segmentRepository.findById(segmentId)
                                .switchIfEmpty(Mono.error(new CustomerDomainException("Customer segment not found: " + segmentId))))
                                .flatMap(pair ->
                                { if (assign) pair.getT1().assignSegment(segmentId); else pair.getT1()
                                        .removeSegment(segmentId); return customerRepository.save(pair.getT1()).then(); });
    }
}
