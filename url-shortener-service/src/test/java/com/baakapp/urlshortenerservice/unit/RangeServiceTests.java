package com.baakapp.urlshortenerservice.unit;

import com.baakapp.urlshortenerservice.client.RangeClient;
import com.baakapp.urlshortenerservice.model.Range;
import com.baakapp.urlshortenerservice.service.impl.RangeServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class RangeServiceTests {

    @Mock
    RangeClient rangeClient;

    @InjectMocks
    RangeServiceImpl rangeService;

    @DisplayName("Fetch token range if start and end range is null")
    @Test
    void whenTokenRangeIsNullThenFetchTokenRange() {
        when(rangeClient.fetchRange()).thenReturn(new Range(1L, 4L));

        rangeService.getUniqueNumber();
        rangeService.getUniqueNumber();
        rangeService.getUniqueNumber();

        verify(rangeClient, times(1)).fetchRange();
    }

    @DisplayName("Fetch new token range if out of range")
    @Test
    void whenTokenRangeOutOfRangeThenFetchTokenRange() {
        when(rangeClient.fetchRange()).thenReturn(new Range(6L, 8L)).thenReturn(new Range(10L, 15L));

        assertEquals(7L, rangeService.getUniqueNumber());
        assertEquals(8L, rangeService.getUniqueNumber());
        assertEquals(11L, rangeService.getUniqueNumber());

        verify(rangeClient, times(2)).fetchRange();
    }
}
