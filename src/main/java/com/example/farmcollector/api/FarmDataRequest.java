package com.example.farmcollector.api;

import com.example.farmcollector.dto.CropDTO;
import com.example.farmcollector.dto.FarmDTO;
import com.example.farmcollector.dto.FarmerDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FarmDataRequest {
    private FarmerDTO farmer;
    private List<FarmDTO> farms;
    private List<CropDTO> crops;
}
