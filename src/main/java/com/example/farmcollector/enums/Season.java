package com.example.farmcollector.enums;

public enum Season {
    YALA("yala"),
    MAHA("maha");

    public String getValue() {
        return value;
    }

    private final String value;

    Season(String value) {
        this.value = value;
    }

}
