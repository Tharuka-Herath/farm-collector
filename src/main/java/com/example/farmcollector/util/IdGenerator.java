package com.example.farmcollector.util;

/**
 * Utility class for generating unique IDs for crops, farms, and farmers.
 */
public class IdGenerator {
    private static int cropIdCounter = 0;
    private static int farmIdCounter = 0;
    private static int farmerIdCounter = 0;

    /**
     * Generates a unique ID for a crop.
     *
     * @return A string representing the generated crop ID.
     */
    public static String generateCropId() {
        return String.format("C-%04d", cropIdCounter++);
    }

    /**
     * Generates a unique ID for a farm.
     *
     * @return A string representing the generated farm ID.
     */
    public static String generateFarmId() {
        return String.format("L-%04d", farmIdCounter++);
    }

    /**
     * Generates a unique ID for a farmer.
     *
     * @return A string representing the generated farmer ID.
     */
    public static String generateFarmerId() {
        return String.format("F-%04d", farmerIdCounter++);
    }
}