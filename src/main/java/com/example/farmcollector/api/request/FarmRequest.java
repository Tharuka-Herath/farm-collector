package com.example.farmcollector.api.request;

import com.example.farmcollector.model.Farmer;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FarmRequest {

    @NotNull
    private String farmName;
    @NotNull
    private String location;
    @NotNull
    private Double farmArea;
    @NotNull
    private List<Farmer> farmers;
}
