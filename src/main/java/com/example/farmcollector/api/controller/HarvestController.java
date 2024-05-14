package com.example.farmcollector.api.controller;

import com.example.farmcollector.dto.HarvestDTO;
import com.example.farmcollector.service.HarvestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HarvestController {
    private final HarvestService harvestService;

    public HarvestController(HarvestService harvestService) {
        this.harvestService = harvestService;
    }

    @PostMapping
    public ResponseEntity<HarvestDTO> saveHarvestDetails(@RequestBody HarvestDTO harvestDTO) {
        HarvestDTO savedHarvestDetails = harvestService.saveHarvestDetails(harvestDTO);
        if (savedHarvestDetails != null) {
            return new ResponseEntity<>(harvestDTO,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
