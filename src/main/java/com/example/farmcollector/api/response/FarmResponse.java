package com.example.farmcollector.api.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FarmResponse {
    private String farmName;
    private String location;
    private Double farmArea;
}
