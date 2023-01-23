package com.baakapp.rangeservice.model;

import com.baakapp.rangeservice.entity.Range;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@NoArgsConstructor(access= AccessLevel.PRIVATE)
public class RangeOperations {

    @Value("${range.start-adder}")
    private static Long startAdder;

    @Value("${range.end-adder}")
    private static Long endAdder;

    public static void incrementRange(Range range) {
        range.setStart(range.getEnd() + startAdder);
        range.setEnd(range.getEnd() + endAdder);
    }
}
