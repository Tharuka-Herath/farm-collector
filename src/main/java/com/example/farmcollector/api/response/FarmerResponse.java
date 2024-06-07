package com.example.farmcollector.api.response;

import com.example.farmcollector.model.Farm;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FarmerResponse {
    private String farmerName;
    private Farm farm;

}
