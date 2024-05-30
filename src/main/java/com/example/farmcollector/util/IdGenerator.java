package com.example.farmcollector.util;

public class IdGenerator {
    private static int cropIdCounter = 0;
    private static int farmIdCounter = 0;
    private static int farmerIdCounter = 0;

    public static String generateCropId() {
        return String.format("C-%04d", cropIdCounter++);
    }

    public static String generateFarmId() {
        return String.format("L-%04d", farmIdCounter++);
    }

    public static String generateFarmerId() {
        return String.format("F-%04d", farmerIdCounter++);
    }
}
