package com.example.farmcollector.api;

import com.example.farmcollector.dto.FarmDataDTO;
import com.example.farmcollector.service.FarmDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/farmdata")
public class FarmDataController {
    private final FarmDataService farmDataService;

    public FarmDataController(FarmDataService farmDataService) {
        this.farmDataService = farmDataService;
    }

    @PostMapping
    public ResponseEntity<String> saveFarmData(@RequestBody FarmDataDTO farmDataDTO) {
        farmDataService.saveFarmData(farmDataDTO);
        return ResponseEntity.ok("Farm data saved successfully.");
    }

}
