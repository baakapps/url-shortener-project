package com.baakapp.rangeservice;

import com.baakapp.rangeservice.controller.RangeController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class RangeServiceApplicationTests {
    @Autowired
    private RangeController rangeController;

    @Test
    void contextLoads() {
        assertNotNull(rangeController);
    }

}
