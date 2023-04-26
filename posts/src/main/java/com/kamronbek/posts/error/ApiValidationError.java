package com.kamronbek.posts.error;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ApiValidationError extends ApiSubError {
    private String object;
    private String field;
    private Object rejectedValue;
    private String message;
}
