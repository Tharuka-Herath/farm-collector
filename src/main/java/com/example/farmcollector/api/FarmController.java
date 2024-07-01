package com.example.farmcollector.api;

import com.example.farmcollector.api.request.FarmRequest;
import com.example.farmcollector.api.response.FarmResponse;
import com.example.farmcollector.dto.FarmDTO;
import com.example.farmcollector.service.farm.FarmService;
import com.example.farmcollector.util.FarmMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@ControllerAdvice
@RestController
@RequiredArgsConstructor
@RequestMapping("/farms")
public class FarmController {

    private final FarmService farmService;
    private final FarmMapper farmMapper;

    @PostMapping
    public ResponseEntity<Object> saveFarm(@RequestBody FarmRequest farmRequest) {
        FarmDTO farmDTO = farmService.saveFarm(farmMapper.convertFarmRequestToDto(farmRequest));
        return ResponseEntity.status(HttpStatus.CREATED).body(farmMapper.convertDtoToResponse(farmDTO));
    }

    @PutMapping("/{farmId}")
    public ResponseEntity<FarmResponse> updateFarm(@PathVariable String farmId, @RequestBody FarmRequest farmRequest) {
        FarmDTO farmDTO = farmMapper.convertFarmRequestToDto(farmRequest);

        FarmDTO updatedFarm = farmService.updateFarm(farmId, farmDTO);
        return ResponseEntity.status(HttpStatus.OK).body(farmMapper.convertDtoToResponse(updatedFarm));
    }

    @GetMapping
    public ResponseEntity<List<FarmResponse>> getAllFarms() {
        List<FarmDTO> allFarms = farmService.getAllFarms();
        List<FarmResponse> responseList = farmMapper.convertDtoListToResponseList(allFarms);
        return ResponseEntity.status(HttpStatus.OK).body(responseList);
    }

    @GetMapping("/{farmId}")
    public ResponseEntity<Object> getFarmById(@PathVariable String farmId) {
        FarmResponse farmResponse = farmMapper.convertDtoToResponse(farmService.getFarmById(farmId));
        return ResponseEntity.status(HttpStatus.OK).body(farmResponse);
    }

    @DeleteMapping("/{farmId}")
    public ResponseEntity<Void> deleteFarm(@PathVariable String farmId) {

        farmService.deleteFarmById(farmId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/{farmId}/farmers/{farmerId}")
    public ResponseEntity<FarmResponse> addFarmerToFarm(@PathVariable String farmId, @PathVariable String farmerId) {

        FarmResponse farmResponse = farmMapper.convertDtoToResponse(farmService.addFarmerToFarm(farmId, farmerId));
        return ResponseEntity.status(HttpStatus.OK).body(farmResponse);
    }
}