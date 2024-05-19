package com.example.farmcollector.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Crop {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cropId;

    @Column(name = "crop_name")
    private String cropName;

    @Column(name = "expected_amount")
    private Double expectedAmount;

    @Column(name = "actual_amount")
    private Double actualAmount;

    @ManyToOne
    @JoinColumn(name = "farm_id")
    private Farm farm;
}
