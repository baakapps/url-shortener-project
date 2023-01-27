package com.baakapp.rangeservice.controller;

import com.baakapp.rangeservice.model.response.RangeResponse;
import com.baakapp.rangeservice.service.RangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
        value = "/api/v1",
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class RangeController {

    private final RangeService rangeService;

    @GetMapping(value = "/range")
    public ResponseEntity<RangeResponse> getRange() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(rangeService.getRange());
    }
}
