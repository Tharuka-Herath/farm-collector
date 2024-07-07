package com.example.farmcollector.api.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FarmerRequest {

    @NotNull(message = "Farmer Name is required")
    @Size(min = 2, max = 50, message = "Farmer Name must be between 2 and 50 characters")
    private String farmerName;
}

