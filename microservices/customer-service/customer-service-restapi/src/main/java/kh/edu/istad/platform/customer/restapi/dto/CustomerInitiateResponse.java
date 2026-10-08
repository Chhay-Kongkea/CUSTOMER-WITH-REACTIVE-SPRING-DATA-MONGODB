package kh.edu.istad.platform.customer.restapi.dto;

import java.util.UUID;

public record CustomerInitiateResponse(
        UUID CustomerId,
        String username,
        String familyName,
        String givenName,
        String email,
        String phoneNumber
) {
}
