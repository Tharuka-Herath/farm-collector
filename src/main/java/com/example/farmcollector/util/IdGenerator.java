package com.example.farmcollector.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicLong;

public class IdGenerator {

    private static final long CUSTOM_EPOCH = 1609459200000L; // Jan 1, 2021

    // To generate a unique counter in a thread-safe manner
    private static final AtomicLong counter = new AtomicLong(0);

    private static final String BASE62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String generateId(String entityCode) {
        long timestamp = Instant.now().toEpochMilli() - CUSTOM_EPOCH;
        long uniqueId = counter.incrementAndGet();
        long combinedId = (timestamp << 20) | (uniqueId & 0xFFFFF); // Shift timestamp and combine with counter
        return entityCode+toBase62(combinedId);
    }


    private static String toBase62(long value) {
        StringBuilder result = new StringBuilder();
        while (value > 0) {
            result.append(BASE62.charAt((int) (value % 62)));
            value /= 62;
        }
        return result.reverse().toString();
    }
}