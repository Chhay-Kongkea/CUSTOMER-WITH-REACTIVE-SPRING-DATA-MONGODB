package kh.edu.istad.platform.customer.restapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CustomerUpdateRequest(
        @NotBlank(message = "Family name is required")
        @Size(max = 100, message = "Family name must not exceed 100 characters")
        String familyName,

        @NotBlank(message = "Given name is required")
        @Size(max = 100, message = "Given name must not exceed 100 characters")
        String givenName
) {
}
