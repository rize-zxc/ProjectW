package com.example.postproject.services;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RequestCounterTest {
    @Test
    void testCounterIncrement() {
        RequestCounter counter = new RequestCounter();
        assertEquals(1, counter.increment());
        assertEquals(2, counter.increment());
    }

    @Test
    void testGetCount() {
        RequestCounter counter = new RequestCounter();
        counter.increment();
        assertEquals(1, counter.getCount());
    }

    @Test
    void testReset() {
        RequestCounter counter = new RequestCounter();
        counter.increment();
        counter.reset();
        assertEquals(0, counter.getCount());
    }
}