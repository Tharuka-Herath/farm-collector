package com.example.farmcollector.enums;

import lombok.Getter;

@Getter
public enum Season {
    YALA("yala"),
    MAHA("maha");
    private final String value;

    Season(String value) {
        this.value = value;
    }

}
