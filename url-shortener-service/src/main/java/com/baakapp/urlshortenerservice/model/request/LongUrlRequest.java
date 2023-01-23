package com.baakapp.urlshortenerservice.model.request;

import com.baakapp.urlshortenerservice.validation.ValidUrl;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LongUrlRequest(
        @NotNull(message = "Url can not be null")
        @NotBlank(message = "Url can not be empty")
        @ValidUrl
        String longUrl
) {}