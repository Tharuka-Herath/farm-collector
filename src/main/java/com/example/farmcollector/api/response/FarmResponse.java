package com.example.farmcollector.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FarmResponse {

    private String farmId;
    private String farmName;
    private String location;
    private Double farmArea;
}

