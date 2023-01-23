package com.baakapp.urlshortenerservice.service.impl;

import com.baakapp.urlshortenerservice.client.RangeClient;
import com.baakapp.urlshortenerservice.model.Range;
import com.baakapp.urlshortenerservice.service.RangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RangeServiceImpl implements RangeService {

    private final RangeClient rangeClient;

    private static Range range = new Range();

    public synchronized Long getUniqueNumber() {
        if(areRangeValuesNullOrOutOfRange()) range = rangeClient.fetchRange();

        incrementRangeByOne();

        return range.getStart();
    }

    private boolean areRangeValuesNullOrOutOfRange() {
        return  range.getStart() == null ||
                range.getEnd() == null   ||
                range.getStart() < 1     ||
                range.getStart() >= range.getEnd();
    }

    private void incrementRangeByOne() {
        range.setStart(range.getStart() + 1);
    }
}
