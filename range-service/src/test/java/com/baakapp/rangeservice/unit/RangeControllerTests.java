package com.baakapp.rangeservice.unit;

import com.baakapp.rangeservice.controller.RangeController;
import com.baakapp.rangeservice.model.response.RangeResponse;
import com.baakapp.rangeservice.service.RangeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = RangeController.class)
class RangeControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    RangeService rangeService;

    @DisplayName("Generate range")
    @Test
    void whenValidLongUrlProvidedThenReturnsShortUrl() throws Exception {
        RangeResponse rangeResponse = new RangeResponse(1L, 1000L);

        RequestBuilder requestBuilder =
                MockMvcRequestBuilders
                        .get("/api/v1/range")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON);
        when(rangeService.getRange()).thenReturn(rangeResponse);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String responseBodyAsString = mvcResult.getResponse().getContentAsString();
        RangeResponse generatedRangeResponse = new ObjectMapper().readValue(responseBodyAsString, RangeResponse.class);

        assertEquals(rangeResponse.start(), generatedRangeResponse.start());
        assertEquals(rangeResponse.end(), generatedRangeResponse.end());
    }


}
