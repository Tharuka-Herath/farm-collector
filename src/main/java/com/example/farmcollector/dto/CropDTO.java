package com.example.farmcollector.dto;

import com.example.farmcollector.enums.Season;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CropDTO {

    private String cropId;
    private String cropType;
    private Season season;
    private Integer yieldYear;
    private Double expectedAmount;
    private Double actualAmount;

    @NotNull(message = "Farmer ID is required")
    private Long farmerId;

    @NotNull(message = "Farm ID is required")
    private Long farmId;
}
