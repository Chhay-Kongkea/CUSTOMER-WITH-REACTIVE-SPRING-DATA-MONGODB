package kh.edu.istad.platform.customer.domain.dto;


public record CustomerFilter(
        String username,
        String familyName,
        String givenName,
        String email,
        String phoneNumber,
        String customerStatus,
        String segmentId,
        Integer page,
        Integer size
) {
    public int normalizedPage() {

        return page == null || page < 0 ? 0 : page;
    }

    public int normalizedSize() {

        return size == null ? 20 : Math.max(1, Math.min(size, 100));
    }
}