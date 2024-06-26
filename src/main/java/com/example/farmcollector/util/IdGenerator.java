package com.example.farmcollector.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicLong;

public class IdGenerator {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS").withZone(ZoneId.systemDefault());
    // To generate a unique counter in a thread-safe manner
    private static final AtomicLong counter = new AtomicLong(0);

    public static String generateId(String entityCode) {
        String timestamp = formatter.format(Instant.now());
        long uniqueId = counter.incrementAndGet();
        return entityCode + timestamp + uniqueId;
    }
}