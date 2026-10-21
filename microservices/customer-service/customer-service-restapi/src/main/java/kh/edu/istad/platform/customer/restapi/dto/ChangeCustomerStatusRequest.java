package kh.edu.istad.platform.customer.restapi.dto;

import jakarta.validation.constraints.NotNull;
import kh.edu.istad.platform.customer.domain.valueobject.CustomerStatus;

public record ChangeCustomerStatusRequest(@NotNull CustomerStatus status) { }
