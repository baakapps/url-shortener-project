package com.baakapp.urlshortenerservice.unit;

import com.baakapp.urlshortenerservice.model.Base62Converter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class Base62ConverterTests {

    @DisplayName("Create short url from long url")
    @Test
    void whenValidatedArgumentsProvidedThenReturnShortUrl() {
        String shortUrl = Base62Converter.encodeUniqueNumberToUniqueString(1L);

        assertEquals("1", shortUrl);
    }
}
