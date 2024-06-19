package com.example.farmcollector.api.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FarmRequest {

    @NotNull
    private String farmName;
    @NotNull
    private String location;
    @NotNull
    private Double farmArea;
}
