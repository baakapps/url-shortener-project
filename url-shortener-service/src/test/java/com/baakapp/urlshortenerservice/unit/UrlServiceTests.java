package com.baakapp.urlshortenerservice.unit;

import com.baakapp.urlshortenerservice.entity.Url;
import com.baakapp.urlshortenerservice.model.Base62Converter;
import com.baakapp.urlshortenerservice.model.response.ShortUrlResponse;
import com.baakapp.urlshortenerservice.repository.UrlRepository;
import com.baakapp.urlshortenerservice.service.RangeService;
import com.baakapp.urlshortenerservice.service.impl.UrlServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UrlServiceTests {

    @InjectMocks
    UrlServiceImpl urlService;
    @Mock
    UrlRepository urlRepository;
    @Mock
    RangeService rangeService;

    private static final String LONG_URL = "https://www.google.com";
    private static final String SHORT_URL = "1";

    @DisplayName("Call save method once for mongoDB")
    @Test
    void whenGivenCorrectThenCallsSaveOnce() {
        urlService.createShortUrlFromLongUrl(LONG_URL);

        verify(urlRepository, times(1)).save(ArgumentMatchers.any(Url.class));
    }

    @DisplayName("Create short url from long url")
    @Test
    void whenValidatedArgumentsProvidedThenReturnShortUrl() {
        mockStatic(Base62Converter.class);

        when(Base62Converter.encodeUniqueNumberToUniqueString(any(Long.class))).thenReturn("1");

        ShortUrlResponse shortUrlResponse = urlService.createShortUrlFromLongUrl(LONG_URL);

        assertEquals(SHORT_URL, shortUrlResponse.shortUrl());
    }

    @DisplayName("Fetch long url from short url")
    @Test
    void whenValidatedArgumentsProvidedThenReturnLongUrl() {
        Optional<Url> url = Optional.of(new Url(SHORT_URL, LONG_URL));
        when(urlRepository.findByShortUrl(SHORT_URL)).thenReturn(url);

        Url generatedUrl = urlService.getLongUrlFromShortUrl(SHORT_URL);

        assertEquals(SHORT_URL, generatedUrl.getShortUrl());
        assertEquals(LONG_URL, generatedUrl.getLongUrl());
    }

    @DisplayName("Throws error when long url is not found")
    @Test
    void whenUrlNotFoundThenThrowsError() {
        when(urlRepository.findByShortUrl(SHORT_URL)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> urlService.getLongUrlFromShortUrl(SHORT_URL));
    }
}
