package com.example.farmcollector.api;

import com.example.farmcollector.api.request.FarmDataRequest;
import com.example.farmcollector.api.response.FarmDataResponse;
import com.example.farmcollector.service.FarmDataService;
import com.example.farmcollector.util.FarmDataMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/farmData")
public class FarmDataController {
    private final FarmDataService farmDataService;

    public FarmDataController(FarmDataService farmDataService) {
        this.farmDataService = farmDataService;
    }

    @PostMapping
    public ResponseEntity<FarmDataResponse> saveFarmData(@RequestBody FarmDataRequest farmDataRequest) {
        FarmDataResponse response = FarmDataMapper.convertDtoToResponse(farmDataService.saveFarmData(FarmDataMapper.convertRequestToDto(farmDataRequest)));
        return ResponseEntity.ok(response);
    }
}
