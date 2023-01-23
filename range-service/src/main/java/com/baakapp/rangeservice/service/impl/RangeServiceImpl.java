package com.baakapp.rangeservice.service.impl;

import com.baakapp.rangeservice.entity.Range;
import com.baakapp.rangeservice.model.RangeOperations;
import com.baakapp.rangeservice.model.response.RangeResponse;
import com.baakapp.rangeservice.repository.RangeRepository;
import com.baakapp.rangeservice.service.RangeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RangeServiceImpl implements RangeService {
    private final RangeRepository rangeRepository;

    @Transactional
    public RangeResponse getRange() {
        Range range = rangeRepository
                .findTopBy()
                .orElse(new Range());

        RangeOperations.incrementRange(range);

        rangeRepository.save(range);

        return new RangeResponse(range.getStart(), range.getEnd());
    }
}