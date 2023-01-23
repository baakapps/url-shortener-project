package com.baakapp.urlshortenerservice.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import com.baakapp.urlshortenerservice.validation.ValidUrl;

public record ShortUrlRequest(
        @NotNull(message = "Url can not be null")
        @NotBlank(message = "Url can not be empty")
        @ValidUrl
        String shortUrl
) {}