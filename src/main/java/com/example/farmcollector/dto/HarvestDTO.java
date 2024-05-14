package com.example.farmcollector.dto;

import com.example.farmcollector.enums.Season;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HarvestDTO {
    private long harvestId;
    private String farmName;
    private Season season;
    private String cropName;
    private double actualAmount;

}
