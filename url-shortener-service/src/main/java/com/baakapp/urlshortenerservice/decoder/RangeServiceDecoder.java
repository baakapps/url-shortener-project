package com.baakapp.urlshortenerservice.decoder;

import feign.Response;
import feign.codec.ErrorDecoder;
import jakarta.ws.rs.InternalServerErrorException;

public class RangeServiceDecoder implements ErrorDecoder {
    private final ErrorDecoder errorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        return response.status() != 200 ?
                new InternalServerErrorException() :
                errorDecoder.decode(methodKey, response);
        }
}