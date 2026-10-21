package kh.edu.istad.platform.customer.domain.dto;

import java.util.Map;

public record CustomerStatisticsResult(
        long totalCustomers,
        Map<String, Long> byStatus,
        Map<String, Long> bySegment)
{ }
