package com.example.farmcollector.api.request;

import com.example.farmcollector.enums.Season;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CropRequest {

    @NotNull(message = "Crop Type is required")
    @Size(min = 2, max = 50, message = "Crop Type must be between 2 and 50 characters")
    private String cropType;

    @NotNull(message = "Season is required")
    private Season season;

    @NotNull(message = "Yield Year is required")
    @Min(value = 1900, message = "Yield Year must be greater than or equal to 1900")
    @Max(value = 2100, message = "Yield Year must be less than or equal to 2100")
    private Integer yieldYear;

    @NotNull(message = "Expected Amount is required")
    @PositiveOrZero(message = "Expected Amount must be a positive number or zero")
    private Double expectedAmount;

    @NotNull(message = "Actual Amount is required")
    @PositiveOrZero(message = "Actual Amount must be a positive number or zero")
    private Double actualAmount;

    @NotNull(message = "Farm ID is required")
    private String farmId;

    @NotNull(message = "Farmer ID is required")
    private String farmerId;

}