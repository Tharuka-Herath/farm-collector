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
    public ResponseEntity<FarmResponse> createFarm(@RequestBody FarmRequest farmRequest) {
        FarmDTO requestDTO = farmService.saveFarm(farmMapper.convertFarmRequestToDto(farmRequest));
        return new ResponseEntity<>(farmMapper.convertDtoToResponse(requestDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FarmResponse> updateFarm(@PathVariable Long id, @RequestBody FarmRequest farmRequest) {
        Optional<FarmDTO> updatedFarm = farmService.updateFarm(id, farmMapper.convertFarmRequestToDto(farmRequest));

        if (updatedFarm.isPresent()) {
            return new ResponseEntity<>(farmMapper.convertDtoToResponse(updatedFarm.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<FarmResponse>> getAllFarms() {
        List<FarmDTO> allFarms = farmService.getAllFarms();
        List<FarmResponse> responseList = farmMapper.convertDtoListToResponseList(allFarms);
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getFarmById(@PathVariable Long id) {
        try {
            FarmResponse farmResponse = farmMapper.convertDtoToResponse(farmService.getFarmById(id));
            return new ResponseEntity<>(farmResponse, HttpStatus.OK);
        } catch (FarmDataNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFarm(@PathVariable Long id) {
        farmService.deleteFarm(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}