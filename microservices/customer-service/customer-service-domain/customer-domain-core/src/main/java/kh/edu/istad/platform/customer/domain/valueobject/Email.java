package kh.edu.istad.platform.customer.domain.valueobject;

public record Email(
        String value
) {
    public Email {
        if (value == null || value.isBlank() ||
                !value.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
            throw new IllegalArgumentException("Email format is invalid");
        }
        value = value.trim().toLowerCase();
    }
}
