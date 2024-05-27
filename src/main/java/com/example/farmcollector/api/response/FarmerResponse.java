package com.example.farmcollector.api.response;

import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FarmerResponse {
    private Long id;
    private String farmerId;
    private String farmerName;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private Set<FarmResponse> farms;
    private CropResponse crop;
}
