package com.baakapp.rangeservice.model;

import com.baakapp.rangeservice.entity.Range;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access= AccessLevel.PRIVATE)
public class RangeOperations {
    public static void incrementRange(Range range, Long start, Long end) {
        range.setStart(range.getEnd() + start);
        range.setEnd(range.getEnd() + end);
    }
}
