package com.baakapp.urlshortenerservice.config;

import com.baakapp.urlshortenerservice.decoder.RangeServiceDecoder;
import feign.Logger;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;

public class RangeClientConfig {

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new RangeServiceDecoder();
    }
}
