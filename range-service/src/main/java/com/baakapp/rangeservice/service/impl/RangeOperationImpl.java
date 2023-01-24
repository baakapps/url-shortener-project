package com.baakapp.rangeservice.service.impl;

import com.baakapp.rangeservice.entity.Range;
import com.baakapp.rangeservice.service.RangeOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RangeOperationImpl implements RangeOperation {

    @Value("${range.startAdder}")
    private Long startAdder;

    @Value("${range.endAdder}")
    private Long endAdder;

    public void incrementRange(Range range) {
        range.setStart(range.getEnd() + startAdder);
        range.setEnd(range.getEnd() + endAdder);
    }
}
