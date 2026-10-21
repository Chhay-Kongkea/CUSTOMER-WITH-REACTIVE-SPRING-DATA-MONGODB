package kh.edu.istad.platform.customer.restapi.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
public record CustomerSegmentRequest(@NotBlank @Size(max = 50) String name, @Size(max = 255) String description) { }
