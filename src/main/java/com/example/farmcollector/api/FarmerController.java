package com.example.farmcollector.api;

import com.example.farmcollector.api.request.FarmerRequest;
import com.example.farmcollector.api.response.FarmerResponse;
import com.example.farmcollector.dto.FarmerDTO;
import com.example.farmcollector.exception.FarmDataNotFoundException;
import com.example.farmcollector.service.farmer.FarmerService;
import com.example.farmcollector.util.FarmerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/farmers")
public class FarmerController {

    private final FarmerService farmerService;
    private final FarmerMapper farmerMapper;

    @PostMapping
    public ResponseEntity<FarmerResponse> saveFarmer(@RequestBody FarmerRequest request) {
        FarmerDTO farmerDTO = farmerMapper.convertFarmerRequestToDto(request);
        FarmerResponse farmerResponse = farmerMapper.convertFarmerDtoToResponse(farmerService.saveFarmer(farmerDTO));

        return ResponseEntity.status(HttpStatus.CREATED).body(farmerResponse);
    }

    @GetMapping
    public ResponseEntity<List<FarmerResponse>> getAllFarmers() {
        List<FarmerDTO> farmersList = farmerService.getAllFarmers();
        List<FarmerResponse> farmerList = farmersList.stream().map(farmerMapper::convertFarmerDtoToResponse).toList();

        return ResponseEntity.status(HttpStatus.OK).body(farmerList);
    }

    @GetMapping("/{farmerId}")
    public ResponseEntity<FarmerResponse> getFarmerById(@PathVariable String farmerId) {
        try {
            FarmerDTO getFarmer = farmerService.getFarmerById(farmerId);
            return ResponseEntity.ok(farmerMapper.convertFarmerDtoToResponse(getFarmer));
        } catch (FarmDataNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{farmerId}")
    public ResponseEntity<FarmerResponse> updateFarmerById(@PathVariable String farmerId, @RequestBody FarmerRequest request) {
        try {
            FarmerDTO farmerUpdate = farmerMapper.convertFarmerRequestToDto(request);
            FarmerDTO updatedFarmer = farmerService.updateFarmerById(farmerId, farmerUpdate);
            FarmerResponse response = farmerMapper.convertFarmerDtoToResponse(updatedFarmer);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (FarmDataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{farmerId}")
    public ResponseEntity<Void> deleteFarmer(@PathVariable String farmerId) {
        try {
            farmerService.deleteFarmerById(farmerId);
            return ResponseEntity.noContent().build();
        } catch (FarmDataNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}