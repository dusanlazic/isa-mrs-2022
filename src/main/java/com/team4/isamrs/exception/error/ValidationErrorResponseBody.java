package com.team4.isamrs.exception.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public class ValidationErrorResponseBody {
    private final Integer status;
    private final String message;
    private final Map<String, String> errors;
}