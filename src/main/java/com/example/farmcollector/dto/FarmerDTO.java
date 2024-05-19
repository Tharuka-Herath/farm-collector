package com.example.farmcollector.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FarmerDTO {
    private Long farmerId;
    private String farmerName;

}
