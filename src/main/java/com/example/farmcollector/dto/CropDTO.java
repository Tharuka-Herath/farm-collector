package com.example.farmcollector.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CropDTO {
    private String cropName;
    private Double expectedAmount;
    private Double actualAmount;

}
