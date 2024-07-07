package com.example.farmcollector.api.request;

import jakarta.validation.constraints.*;
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
    @Digits(message = "Farm Area must be a positive value with maximum of 4 decimal places", integer = 10, fraction = 4)
    @Positive(message = "Farm Area must be a positive number")
    private Double farmArea;
}
