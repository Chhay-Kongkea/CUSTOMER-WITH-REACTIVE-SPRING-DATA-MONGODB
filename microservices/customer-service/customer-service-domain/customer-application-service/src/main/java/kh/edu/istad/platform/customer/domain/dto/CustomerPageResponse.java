package kh.edu.istad.platform.customer.domain.dto;

import java.util.List;

public record CustomerPageResponse<T>(
        List<T> content,
        int page,
        int size,
        long totalElements,
        int totalPages
) {}