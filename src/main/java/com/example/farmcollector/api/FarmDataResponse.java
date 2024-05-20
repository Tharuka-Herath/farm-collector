package com.example.farmcollector.api;

import com.example.farmcollector.enums.Season;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FarmDataResponse {
    private String farmerName;

    private String farmName;
    private Double farmArea;
    private Season season;
    private Integer yieldYear;

    private String cropName;
    private Double expectedAmount;
    private Double actualAmount;
}
