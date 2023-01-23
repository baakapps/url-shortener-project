package com.baakapp.urlshortenerservice.model.exception;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ArgumentValidationError {
    private String error;
    private String title;
    private List<InvalidParam> invalidParams;
}