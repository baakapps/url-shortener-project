package com.baakapp.urlshortenerservice.model.exception;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class InvalidParam {
    private String name;
    private List<String> reasons;
}