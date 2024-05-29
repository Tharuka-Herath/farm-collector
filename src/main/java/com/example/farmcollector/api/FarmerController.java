package com.example.farmcollector.api;

import com.example.farmcollector.api.request.FarmerRequest;
import com.example.farmcollector.api.response.FarmerResponse;
import com.example.farmcollector.dto.FarmerDTO;
import com.example.farmcollector.service.farmer.FarmerService;
import com.example.farmcollector.util.FarmerMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/farmer")
public class FarmerController {

    private final FarmerService farmerService;
    private final FarmerMapper farmerMapper;

    public FarmerController(FarmerService farmerService, FarmerMapper farmerMapper) {
        this.farmerService = farmerService;
        this.farmerMapper = farmerMapper;
    }

    @PostMapping
    public ResponseEntity<FarmerResponse> savedFarmer(@RequestBody FarmerRequest request) {
        FarmerDTO savedFarmer = farmerMapper.convertFarmerRequestToDto(request);
        FarmerResponse farmerResponse = farmerMapper.convertFarmerDtoToResponse(farmerService.saveFarmer(savedFarmer));
        return new ResponseEntity<>(farmerResponse, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<FarmerResponse>> getAllFarmers() {
        List<FarmerDTO> farmersList = farmerService.getAllFarmers();
        List<FarmerResponse> farmerList = farmersList.stream()
                .map(farmerMapper::convertFarmerDtoToResponse)
                .toList();

        return ResponseEntity.ok(farmerList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FarmerResponse> getFarmerById(@PathVariable Long id) {
        return null;
    }
}