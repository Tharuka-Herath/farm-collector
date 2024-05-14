package com.example.farmcollector.dto;

import com.example.farmcollector.enums.Season;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlantationDTO {
    private String farm;
    private double plantedArea;
    private Season season;
    private String cropName;
    private double expectedAmount;
}
