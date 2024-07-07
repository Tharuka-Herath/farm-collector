package com.example.farmcollector.dto;

import com.example.farmcollector.model.Farm;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FarmerDTO {

    private String farmerId;
    private String farmerName;

    @NotNull(message = "Farm is required")
    private Farm farm;
}
