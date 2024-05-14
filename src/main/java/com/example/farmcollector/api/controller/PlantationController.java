package com.example.farmcollector.api.controller;


import com.example.farmcollector.dto.PlantationDTO;
import com.example.farmcollector.service.PlantationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/plantation")
public class PlantationController {
    private final PlantationService plantationService;


    public PlantationController(PlantationService plantationService) {
        this.plantationService = plantationService;
    }


    @PostMapping
    public ResponseEntity<String> savePlantation(@RequestBody PlantationDTO plantationDTO) {
        PlantationDTO savedPlantation = plantationService.savePlantationDetails(plantationDTO);
        if (savedPlantation != null) {
            return new ResponseEntity<>("Plantation saved successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to save plantation", HttpStatus.BAD_REQUEST);
        }
    }
}
