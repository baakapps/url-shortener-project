package com.baakapp.urlshortenerservice.model.exception;

import lombok.Builder;

@Builder
public record ErrorMessage(ErrorDetail errorDetail) {}