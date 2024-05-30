package com.example.farmcollector.api.request;
import com.example.farmcollector.enums.Season;
import lombok.*;



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
}