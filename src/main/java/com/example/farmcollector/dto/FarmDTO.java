package com.example.farmcollector.dto;

import com.example.farmcollector.enums.Season;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FarmDTO {
    private String farmName;
    private Double farmArea;
    private Season season;
    private Integer yieldYear;

}
