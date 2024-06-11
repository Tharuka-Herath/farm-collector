package com.example.farmcollector.dto;

import com.example.farmcollector.model.Farmer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FarmDTO {
    private String farmName;
    private String location;
    private Double farmArea;
    List<Farmer> farmers;
}
