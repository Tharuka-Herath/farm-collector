package com.example.farmcollector.api;

import com.example.farmcollector.api.request.FarmerRequest;
import com.example.farmcollector.api.response.FarmerResponse;
import com.example.farmcollector.dto.FarmerDTO;
import com.example.farmcollector.exception.FarmDataNotFoundException;
import com.example.farmcollector.service.farmer.FarmerService;
import com.example.farmcollector.util.FarmerMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/farmers")
public class FarmerController {

    private final FarmerService farmerService;
    private final FarmerMapper farmerMapper;

    public FarmerController(FarmerService farmerService, FarmerMapper farmerMapper) {
        this.farmerService = farmerService;
        this.farmerMapper = farmerMapper;
    }

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

    @GetMapping("/{id}")
    public ResponseEntity<FarmerResponse> getFarmerById(@PathVariable Long id) {
        try {
            FarmerDTO getFarmer = farmerService.getFarmerById(id);
            return ResponseEntity.ok(farmerMapper.convertFarmerDtoToResponse(getFarmer));
        } catch (FarmDataNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<FarmerResponse> updateFarmerById(@PathVariable Long id, @RequestBody FarmerRequest request) {
        try {
            FarmerDTO farmerUpdate = farmerMapper.convertFarmerRequestToDto(request);
            FarmerDTO updatedFarmer = farmerService.updateFarmerById(id, farmerUpdate);
            FarmerResponse response = farmerMapper.convertFarmerDtoToResponse(updatedFarmer);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (FarmDataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFarmer(@PathVariable Long id) {
        try {
            farmerService.deleteFarmer(id);
            return ResponseEntity.noContent().build();
        } catch (FarmDataNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}