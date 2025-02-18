package com.jeyofdev.yellow_berry.core.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collection;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DomainSuccessResponse<T> {
    @Nullable
    private String message;

    @Nullable
    private Integer count;

    @Nullable
    private T result;

    private HttpStatus status;

    public static <T> ResponseEntity<DomainSuccessResponse<T>> get(HttpStatus status, T result) {
        DomainSuccessResponse<T> response = DomainSuccessResponse.<T>builder()
                .status(status)
                .count(calculateCount(result))
                .result(result)
                .build();

        return ResponseEntity.status(status).body(response);
    }

    public static <T> ResponseEntity<DomainSuccessResponse<T>> get(HttpStatus status, String message) {
        DomainSuccessResponse<T> response = DomainSuccessResponse.<T>builder()
                .status(status)
                .message(message)
                .build();

        return ResponseEntity.status(status).body(response);
    }

    /**
     * Calculate the number of items
     */
    private static Integer calculateCount(Object result) {
        if (result instanceof Collection) {
            return ((Collection<?>) result).size();
        }

        return null;
    }
}
