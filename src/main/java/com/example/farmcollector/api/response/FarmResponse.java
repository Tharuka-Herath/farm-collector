package com.example.farmcollector.api.response;

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
public class FarmResponse {
    private String farmName;
    private String location;
    private Double farmArea;
}
