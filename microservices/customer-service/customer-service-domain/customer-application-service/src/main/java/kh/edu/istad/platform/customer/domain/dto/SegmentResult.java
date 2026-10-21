package kh.edu.istad.platform.customer.domain.dto;

import kh.edu.istad.platform.customer.domain.entity.CustomerSegment;
import java.util.UUID;

public record SegmentResult(
        UUID id,
        String name,
        String description) {

    public SegmentResult(CustomerSegment segment) {
        this(segment.getId(), segment.getName(), segment.getDescription()); }
}
