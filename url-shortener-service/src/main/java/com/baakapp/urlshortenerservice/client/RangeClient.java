package com.baakapp.urlshortenerservice.client;

import com.baakapp.urlshortenerservice.config.RangeClientConfig;
import com.baakapp.urlshortenerservice.model.Range;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "tokenService", configuration = RangeClientConfig.class)
public interface RangeClient {

    @GetMapping(value = "api/v1/range", produces = "application/json", consumes = "application/json")
    Range fetchRange();

}