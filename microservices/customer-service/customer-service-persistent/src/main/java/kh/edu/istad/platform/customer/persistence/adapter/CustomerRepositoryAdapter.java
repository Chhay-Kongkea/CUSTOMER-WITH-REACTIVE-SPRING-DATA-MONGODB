
package kh.edu.istad.platform.customer.persistence.adapter;

import kh.edu.istad.common.domain.valueobject.CustomerId;
import kh.edu.istad.platform.customer.domain.dto.CustomerFilter;
import kh.edu.istad.platform.customer.domain.dto.CustomerStatisticsResult;
import kh.edu.istad.platform.customer.domain.entity.Customer;
import kh.edu.istad.platform.customer.domain.port.out.CustomerRepository;
import kh.edu.istad.platform.customer.persistence.document.CustomerDocument;
import kh.edu.istad.platform.customer.persistence.mapper.CustomerPersistenceMapper;
import kh.edu.istad.platform.customer.persistence.repository.CustomerMongoRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.bson.Document;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.LinkedHashMap;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class CustomerRepositoryAdapter implements CustomerRepository {

    private final CustomerPersistenceMapper customerPersistenceMapper;
    private final CustomerMongoRepository customerMongoRepository;
    private final ReactiveMongoTemplate reactiveMongoTemplate;

    @Override
    public Mono<Customer> save(Customer customer) {
        CustomerDocument document =
                customerPersistenceMapper.toDocument(customer);

        return customerMongoRepository.save(document)
                .map(customerPersistenceMapper::toDomain);
    }

    @Override
    public Flux<Customer> findAll() {
        return customerMongoRepository.findAll()
                .map(customerPersistenceMapper::toDomain);
    }

    @Override
    public Flux<Customer> findByFilter(
            CustomerFilter filter) {

        Query query = buildQuery(filter);

        query.with(Sort.by(Sort.Direction.ASC, "_id"));

        query.skip((long) filter.normalizedPage()
                * filter.normalizedSize());

        query.limit(filter.normalizedSize());

        return reactiveMongoTemplate.find(query, CustomerDocument.class)
                .map(customerPersistenceMapper::toDomain);
    }

    @Override
    public Mono<Long> countByFilter(CustomerFilter filter) {
        return reactiveMongoTemplate.count(
                buildQuery(filter),
                CustomerDocument.class
        );
    }

    private Query buildQuery(CustomerFilter filter) {
        List<Criteria> criteria = new ArrayList<>();

        if (hasText(filter.username())) {
            criteria.add(
                    Criteria.where("username")
                            .regex(
                                    Pattern.quote(filter.username().trim()),
                                    "i"
                            )
            );
        }

        if (hasText(filter.familyName())) {
            criteria.add(
                    Criteria.where("familyName")
                            .regex(
                                    Pattern.quote(filter.familyName().trim()),
                                    "i"
                            )
            );
        }

        if (hasText(filter.givenName())) {
            criteria.add(
                    Criteria.where("givenName")
                            .regex(
                                    Pattern.quote(filter.givenName().trim()),
                                    "i"
                            )
            );
        }

        if (hasText(filter.email())) {
            criteria.add(
                    Criteria.where("email")
                            .is(filter.email().trim())
            );
        }

        if (hasText(filter.phoneNumber())) {
            criteria.add(
                    Criteria.where("phoneNumber")
                            .is(filter.phoneNumber().trim())
            );
        }

        if (filter.customerStatus() != null) {
            criteria.add(
                    Criteria.where("customerStatus")
                            .is(filter.customerStatus())
            );
        }

        if (hasText(filter.segmentId())) {
            criteria.add(
                    Criteria.where("segmentIds")
                            .is(filter.segmentId().trim())
            );
        }

        if (criteria.isEmpty()) {
            return new Query();
        }

        return new Query(
                new Criteria().andOperator(
                        criteria.toArray(new Criteria[0])
                )
        );
    }

    private boolean hasText(String value) {
        return value != null && !value.isBlank();
    }
    @Override
    public Mono<Customer> findById(CustomerId id) {
        return customerMongoRepository.findById(id.id().toString())
                .map(customerPersistenceMapper::toDomain);
    }

    @Override
    public Mono<Void> deleteById(CustomerId id) {
        return customerMongoRepository.deleteById(id.id().toString()).then();

    }

    @Override
    public Mono<Customer> findByUsername(String username) {
        return customerMongoRepository.findByUsername(username)
                .map(customerPersistenceMapper::toDomain);
    }

    @Override
    public Mono<CustomerStatisticsResult> statistics() {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.facet(Aggregation.count().as("count")).as("totalCustomers")
                        .and(Aggregation.group("customerStatus").count().as("count")).as("byStatus")
                        .and(
                                Aggregation.unwind("segmentIds"),
                                Aggregation.lookup("customer_segments", "segmentIds", "_id", "segment"),
                                Aggregation.unwind("segment"),
                                Aggregation.group("segment.name").count().as("count")
                        ).as("bySegment")
        );

        return reactiveMongoTemplate.aggregate(aggregation, "customers", Document.class)
                .next()
                .defaultIfEmpty(new Document())
                .map(this::toStatistics);
    }

    private CustomerStatisticsResult toStatistics(Document document) {
        long total = 0;
        Map<String, Long> byStatus = new LinkedHashMap<>();
        Map<String, Long> bySegment = new LinkedHashMap<>();

        Object totalValue = document.get("totalCustomers");
        if (totalValue instanceof List<?> totals && !totals.isEmpty()) {
            total = number(((Document) totals.get(0)).get("count"));
        }
        readCounts(document.get("byStatus"), byStatus);
        readCounts(document.get("bySegment"), bySegment);
        return new CustomerStatisticsResult(total, byStatus, bySegment);
    }

    private void readCounts(Object value, Map<String, Long> target) {
        if (!(value instanceof List<?> values)) return;
        for (Object item : values) {
            Document row = (Document) item;
            Object key = row.get("_id");
            if (key != null) target.put(key.toString(), number(row.get("count")));
        }
    }

    private long number(Object value) {
        return value instanceof Number number ? number.longValue() : 0L;
    }

    @Override
    public Mono<Customer> findByEmail(String email) {
        return customerMongoRepository.findByEmail(email)
                .map(customerPersistenceMapper::toDomain);
    }

    @Override
    public Mono<Customer> findByPhoneNumber(String phoneNumber) {
        return customerMongoRepository.findByPhoneNumber(phoneNumber)
                .map(customerPersistenceMapper::toDomain);
    }

    @Override
    public Flux<Customer> query() {

        Query query = Query.query(
                Criteria.where("familyName").is("Sim")
                        .and("username").is("menghor")
                        .gt(2000)
        );

        return reactiveMongoTemplate.find(query,CustomerDocument.class)
                .map(customerPersistenceMapper::toDomain);
    }
}
