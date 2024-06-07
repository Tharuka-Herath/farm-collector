package com.example.farmcollector.api.response;

import com.example.farmcollector.enums.Season;
import com.example.farmcollector.model.Farm;
import com.example.farmcollector.model.Farmer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CropResponse {
    private String cropType;
    private Season season;
    private Integer yieldYear;
    private Double expectedAmount;
    private Double actualAmount;
    private Farm farm;
    private Farmer farmer;
}

