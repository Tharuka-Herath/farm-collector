package com.example.farmcollector.model;


import com.example.farmcollector.enums.Season;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Type;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "plantation")
public class Plantation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "farm_name")
    private String farm;

    @Column(name = "planted_area")
    private String plantedArea;

    @Enumerated(EnumType.STRING)
    @Column(name = "season")
    private Season season;

    @Column(name = "crop_name")
    private  String cropName;

    @Column(name = "expected_amount")
    private double expectedAmount;


}
