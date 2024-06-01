package com.example.farmcollector.dto;

import com.example.farmcollector.model.Crop;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FarmerDTO {
    private String farmerName;
    private Crop crop;
}
