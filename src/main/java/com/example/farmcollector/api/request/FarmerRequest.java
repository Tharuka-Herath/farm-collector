package com.example.farmcollector.api.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FarmerRequest {
    @NotBlank(message = "Farmer ID id required")
    private String farmerId;
    @NotBlank(message = "Farmer Name is required")
    private String farmerName;
}

