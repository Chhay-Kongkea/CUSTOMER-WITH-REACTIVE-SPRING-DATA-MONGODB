package kh.edu.istad.platform.customer.domain.entity;

import kh.edu.istad.platform.customer.domain.exception.CustomerDomainException;
import java.util.UUID;

public class CustomerSegment {
    private UUID id;
    private String name;
    private String description;
    private CustomerSegment() { }

    public static CustomerSegment create(String name, String description) {
        CustomerSegment segment = new CustomerSegment();
        segment.id = UUID.randomUUID();
        segment.update(name, description);
        return segment;
    }

    public static CustomerSegment restore(UUID id, String name, String description) {
        CustomerSegment segment = new CustomerSegment();
        segment.id = id;
        segment.update(name, description);
        return segment;
    }

    public void update(String name, String description) {
        if (name == null || name.isBlank() || name.length() > 50)
            throw new CustomerDomainException("Segment name must be between 1 and 50 characters");
        this.name = name.trim();
        this.description = description == null ? null : description.trim();
    }
    public UUID getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
}
