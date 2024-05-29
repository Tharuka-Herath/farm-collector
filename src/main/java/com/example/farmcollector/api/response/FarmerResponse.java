package com.example.farmcollector.api.response;

import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FarmerResponse {
    private String farmerId;
    private String farmerName;

}
