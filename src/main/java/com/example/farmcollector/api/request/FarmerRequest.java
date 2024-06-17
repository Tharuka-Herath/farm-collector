package com.example.farmcollector.api.request;

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
public class FarmerRequest {

    @NotNull
    private String farmerName;
}

