package com.example.farmcollector.api.request;
import com.example.farmcollector.enums.Season;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CropRequest {
    private String cropId;
    private String cropType;
    private Season season;
    private Integer yieldYear;
    private Double expectedAmount;
    private Double actualAmount;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private Set<Long> farmIds;
    private Long farmerId;
}