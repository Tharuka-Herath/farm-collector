package com.example.farmcollector.api.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FarmerRequest {
    private String farmerName;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private Set<Long> farmIds;
    private Long cropId;
}

