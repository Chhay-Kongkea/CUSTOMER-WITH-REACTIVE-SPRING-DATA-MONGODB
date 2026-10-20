package kh.edu.istad.platform.customer.restapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
public record CreateCustomerRequest(

        @NotBlank(message = "Username is required")
        @Size(min = 3, max = 50,
                message = "Username must be 3 to 50 characters")
        String username,

        @NotBlank(message = "Family name is required")
        @Size(max = 100)
        String familyName,

        @NotBlank(message = "Given name is required")
        @Size(max = 100)
        String givenName,

        @NotBlank(message = "Email is required")
        @Email(message = "Email format is invalid")
        String email,

        @NotBlank(message = "Phone number is required")
        @Pattern(
                regexp = "^\\+?[0-9]{8,15}$",
                message = "Phone number must contain 8 to 15 digits"
        )
        String phoneNumber
) {
}
