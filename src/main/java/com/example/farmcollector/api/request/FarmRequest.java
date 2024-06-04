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
public class FarmRequest {
    @NotBlank
    private String farmName;
    @NotBlank
    private String location;
    @NotBlank
    private Double farmArea;
}
