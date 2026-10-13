package kh.edu.istad.platform.customer.restapi.exception;

import kh.edu.istad.platform.customer.domain.exception.CustomerDomainException;
import kh.edu.istad.platform.customer.restapi.dto.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class CustomerExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidation(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new LinkedHashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error ->
                errors.putIfAbsent(error.getField(), error.getDefaultMessage())
        );

        return error(HttpStatus.BAD_REQUEST, "Validation failed", errors);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException exception) {
        return error(
                HttpStatus.BAD_REQUEST,
                "customerId must be a valid UUID",
                Map.of("customerId", "customerId must be a valid UUID")
        );
    }

    @ExceptionHandler(CustomerDomainException.class)
    public ResponseEntity<ApiErrorResponse> handleDomainException(CustomerDomainException exception) {
        HttpStatus status = exception.getMessage() != null
                && exception.getMessage().startsWith("Customer not found:")
                ? HttpStatus.NOT_FOUND
                : HttpStatus.BAD_REQUEST;

        return error(status, exception.getMessage(), Map.of());
    }

    private ResponseEntity<ApiErrorResponse> error(
            HttpStatus status,
            String message,
            Map<String, String> errors) {
        return ResponseEntity.status(status).body(
                new ApiErrorResponse(status.value(), message, errors, LocalDateTime.now())
        );
    }
}
