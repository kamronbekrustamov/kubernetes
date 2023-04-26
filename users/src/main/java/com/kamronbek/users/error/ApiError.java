package com.kamronbek.users.error;

import lombok.Getter;

import java.time.Instant;
import java.util.List;

@Getter
public class ApiError {
    private final Integer status;
    private final String message;
    private final Long timestamp;
    private final List<ApiSubError> subErrors;

    public ApiError(Integer status, String message) {
        this(status, message, null);
    }

    public ApiError(Integer status, String message, List<ApiSubError> subErrors) {
        this.status = status;
        this.message = message;
        this.timestamp = Instant.now().getEpochSecond();
        this.subErrors = subErrors;
    }
}
