package com.example.farmcollector.api.request;

import lombok.*;

@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FarmRequest {
    private String farmName;
    private String location;
    private Double farmArea;
}
