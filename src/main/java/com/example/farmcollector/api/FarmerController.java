package com.example.farmcollector.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/farmer")
public class FarmerController {

    @GetMapping("/all")
    public String getAllFarmers() {
        return "All farmers";
    }
}
