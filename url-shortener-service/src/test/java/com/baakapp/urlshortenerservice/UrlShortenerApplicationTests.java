package com.baakapp.urlshortenerservice;

import com.baakapp.urlshortenerservice.controller.UrlController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = UrlShortenerApplicationTests.class)
class UrlShortenerApplicationTests {
    @Autowired
    private UrlController urlController;

    @Test
    void contextLoads() {
        assertNotNull(urlController);
    }

}
