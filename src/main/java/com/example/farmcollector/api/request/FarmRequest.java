package com.example.farmcollector.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FarmRequest {

    @NotBlank(message = "Farm Name is required")
    @Size(min = 2, max = 50, message = "Farm Name must be between 2 and 50 characters")
    private String farmName;

    @NotBlank(message = "Location is required")
    @Size(min = 2, max = 100, message = "Location must be between 2 and 100 characters")
    private String location;

    @NotNull(message = "Farm Area is required")
    @Positive(message = "Farm Area must be a positive number")
    private Double farmArea;
}
