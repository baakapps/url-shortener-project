package com.baakapp.urlshortenerservice.unit;

import com.baakapp.urlshortenerservice.controller.UrlController;
import com.baakapp.urlshortenerservice.model.request.LongUrlRequest;
import com.baakapp.urlshortenerservice.model.response.ShortUrlResponse;
import com.baakapp.urlshortenerservice.service.UrlService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@WebMvcTest(controllers = UrlController.class)
@AutoConfigureMockMvc(addFilters = false)
class UrlControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UrlService urlService;

    private static final String LONG_URL = "https://www.google.com";
    private static final String SHORT_URL = "1";

    @DisplayName("Short url generation")
    @Test
    void whenValidLongUrlProvidedThenReturnsShortUrl() throws Exception {
        LongUrlRequest longUrlRequest = new LongUrlRequest(LONG_URL);

        RequestBuilder requestBuilder =
                MockMvcRequestBuilders
                        .post("/api/v1/shortUrl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(longUrlRequest));

        ShortUrlResponse shortUrlResponse = new ShortUrlResponse(SHORT_URL);
        when(urlService.createShortUrlFromLongUrl(any(String.class))).thenReturn(shortUrlResponse);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String responseBodyAsString = mvcResult.getResponse().getContentAsString();
        ShortUrlResponse generatedShortUrlResponse = new ObjectMapper().readValue(responseBodyAsString, ShortUrlResponse.class);

        assertEquals(shortUrlResponse.shortUrl(), generatedShortUrlResponse.shortUrl());
    }

}
