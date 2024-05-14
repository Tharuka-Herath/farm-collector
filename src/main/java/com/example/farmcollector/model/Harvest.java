package com.example.farmcollector.model;


import com.example.farmcollector.enums.Season;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "harvest")
public class Harvest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "harvest_id")
    private long harvestId;

    @Column(name = "farm_name")
    private String farmName;

    @Enumerated(EnumType.STRING)
    @Column(name = "season")
    private Season season;

    @Column(name = "crop_name")
    private String cropName;

    @Column(name = "actual_amount")
    private double actualAmount;
}
