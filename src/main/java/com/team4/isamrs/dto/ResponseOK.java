package com.team4.isamrs.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ResponseOK {
    private final Integer status = HttpStatus.OK.value();
    private final String message;

    public ResponseOK(String message) {
        this.message = message;
    }
}