package com.example.farmcollector.api;

import com.example.farmcollector.api.request.FarmRequest;
import com.example.farmcollector.api.response.FarmResponse;
import com.example.farmcollector.dto.FarmDTO;
import com.example.farmcollector.exception.FarmDataNotFoundException;
import com.example.farmcollector.service.farm.FarmService;
import com.example.farmcollector.util.FarmMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/farms")
public class FarmController {

    private final FarmService farmService;
    private final FarmMapper farmMapper;

    public FarmController(FarmService farmService, FarmMapper farmMapper) {
        this.farmService = farmService;
        this.farmMapper = farmMapper;
    }

    @PostMapping
    public ResponseEntity<FarmResponse> saveFarm(@RequestBody FarmRequest farmRequest) {
        FarmDTO farmDTO = farmService.saveFarm(farmMapper.convertFarmRequestToDto(farmRequest));
        return ResponseEntity.status(HttpStatus.CREATED).body(farmMapper.convertDtoToResponse(farmDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FarmResponse> updateFarm(@PathVariable Long id, @RequestBody FarmRequest farmRequest) {
        FarmDTO farmDTO = farmMapper.convertFarmRequestToDto(farmRequest);
        Optional<FarmDTO> updatedFarm = farmService.updateFarm(id, farmDTO);

        return updatedFarm.map(dto -> new ResponseEntity<>(farmMapper.convertDtoToResponse(dto), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<FarmResponse>> getAllFarms() {
        List<FarmDTO> allFarms = farmService.getAllFarms();
        List<FarmResponse> responseList = farmMapper.convertDtoListToResponseList(allFarms);
        return ResponseEntity.status(HttpStatus.OK).body(responseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getFarmById(@PathVariable Long id) {
        try {
            FarmResponse farmResponse = farmMapper.convertDtoToResponse(farmService.getFarmById(id));
            return ResponseEntity.status(HttpStatus.OK).body(farmResponse);
        } catch (FarmDataNotFoundException e) {
            return ResponseEntity.status((HttpStatus.NOT_FOUND)).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFarm(@PathVariable Long id) {
        try {
            farmService.deleteFarm(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (FarmDataNotFoundException e) {
            return ResponseEntity.status((HttpStatus.NOT_FOUND)).build();
        }
    }



}