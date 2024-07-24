package com.example.farmcollector.api.request;

import com.example.farmcollector.enums.Season;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CropRequest {
    private String cropType;
    private Season season;
    private Integer yieldYear;
    private Double expectedAmount;
    private Double actualAmount;
    private String farmId;
    private String farmerId;
}