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

    @NotBlank(message = "Crop Type is required")
    @Size(min = 2, max = 50, message = "Crop Type must be between 2 and 50 characters")
    private String cropType;

    @NotNull(message = "Season is required")
    private Season season;

    @NotNull(message = "Yield Year is required")
    @Digits(message = "Yield year must be an year", integer = 10, fraction = 0)
    @Min(value = 1900, message = "Yield Year must be greater than or equal to 1900")
    @Max(value = 2100, message = "Yield Year must be less than or equal to 2100")
    private Integer yieldYear;

    @NotNull(message = "Expected Amount is required")
    @Digits(message = "Expected Amount must be a positive value with maximum of 4 decimal places", integer = 10, fraction = 4)
    @PositiveOrZero(message = "Expected Amount must be a positive number or zero")
    private Double expectedAmount;

    @NotNull(message = "Actual Amount is required")
    @Digits(message = "Actual Amount must be a positive value with maximum of 4 decimal places", integer = 10, fraction = 4)
    @PositiveOrZero(message = "Actual Amount must be a positive number or zero")
    private Double actualAmount;

    @NotBlank(message = "Farm ID is required")
    private String farmId;

    @NotBlank(message = "Farmer ID is required")
    private String farmerId;

}