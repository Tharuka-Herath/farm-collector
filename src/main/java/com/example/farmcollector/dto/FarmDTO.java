package com.example.farmcollector.dto;

import com.example.farmcollector.model.Crop;
import com.example.farmcollector.model.Farmer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FarmDTO {
    private String farmId;
    private String farmName;
    private String location;
    private Double farmArea;
    private Set<Farmer> farmers;
    private Set<Crop> crops;
}
