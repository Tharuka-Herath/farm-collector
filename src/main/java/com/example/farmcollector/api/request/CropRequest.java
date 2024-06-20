package com.example.farmcollector.api.request;

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
public class CropRequest {

    @NotNull
    private String cropType;
    @NotNull
    private Season season;
    @NotNull
    private Integer yieldYear;
    @NotNull
    private Double expectedAmount;
    @NotNull
    private Double actualAmount;
    @NotNull
    private String farmId;
    @NotNull
    private String farmerId;

}