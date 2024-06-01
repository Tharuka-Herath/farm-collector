package com.example.farmcollector.api.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FarmRequest {
    private String farmName;
    private String location;
    private Double farmArea;
}
