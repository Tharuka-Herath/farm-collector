package com.example.farmcollector.dto;

import com.example.farmcollector.enums.Season;
import com.example.farmcollector.model.Farm;
import com.example.farmcollector.model.Farmer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CropDTO {
    private String cropType;
    private Season season;
    private Integer yieldYear;
    private Double expectedAmount;
    private Double actualAmount;
    private Farmer farmer;
    private Farm farm;
}
