package com.example.farmcollector.api.response;

import com.example.farmcollector.dto.FarmerDTO;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FarmResponse {
    private String farmName;
    private String location;
    private Double farmArea;
    private Set<FarmerDTO> farmers;
}
