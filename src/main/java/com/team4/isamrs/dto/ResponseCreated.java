package com.team4.isamrs.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.UUID;

@Getter
public class ResponseCreated {
    private final Integer status = HttpStatus.CREATED.value();
    private final String message;
    private final String id;

    public ResponseCreated(String message, Long id) {
        this.message = message;
        this.id = id.toString();
    }

    public ResponseCreated(String message, UUID id) {
        this.message = message;
        this.id = id.toString();
    }
}