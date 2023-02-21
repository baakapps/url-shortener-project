package com.baakapp.rangeservice.unit;

import com.baakapp.rangeservice.entity.Range;
import com.baakapp.rangeservice.model.response.RangeResponse;
import com.baakapp.rangeservice.repository.RangeRepository;
import com.baakapp.rangeservice.service.impl.RangeServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = RangeServiceImpl.class)
class RangeServiceTests {

    @MockBean
    RangeRepository rangeRepository;

    @Autowired
    RangeServiceImpl rangeService;

    @DisplayName("Update range")
    @Test
    void whenRangeIsFetchedThenReturnsUpdatedOne() {
        Range range = new Range(0L, 1000L);
        when(rangeRepository.findTopBy()).thenReturn(Optional.of(range));

        RangeResponse rangeResponse = rangeService.getRange();

        assertEquals(range.getStart(), rangeResponse.start());
        assertEquals(range.getEnd(), rangeResponse.end());
    }

    @DisplayName("Call save method once")
    @Test
    void whenRangeIsFetchedThenSave() {
        rangeService.getRange();

        verify(rangeRepository, times(1)).save(ArgumentMatchers.any(Range.class));
    }
}
