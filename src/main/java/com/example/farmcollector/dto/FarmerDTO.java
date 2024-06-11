package com.example.farmcollector.dto;

import com.example.farmcollector.model.Farm;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FarmerDTO {
    private String farmerName;
    private Farm farm;
}
