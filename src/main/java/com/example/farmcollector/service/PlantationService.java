package com.example.farmcollector.service;

import com.example.farmcollector.dto.PlantationDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PlantationService {
    PlantationDTO savePlantationDetails(PlantationDTO plantationDTO);


}