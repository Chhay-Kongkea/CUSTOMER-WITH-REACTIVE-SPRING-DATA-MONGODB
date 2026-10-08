package kh.edu.istad.platform.customer.restapi.dto;

public record CustomerInitiateRequest(

        String username,
        String familyName,
        String givenName,
        String email,
        String phoneNumber

) {
}
