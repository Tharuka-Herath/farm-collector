package com.example.farmcollector.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FarmDataDTO {
    private FarmerDTO farmer;
    private List<FarmDTO> farms;
    private List<CropDTO> crops;
}
