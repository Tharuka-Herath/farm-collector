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
@Table(name = "plantation")
public class Plantation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "plantation_id")
    private long plantationId;

    @Column(name = "farm_name")
    private String farm;

    @Column(name = "planted_area(acres)")
    private double plantedArea;

    @Enumerated(EnumType.STRING)
    @Column(name = "season")
    private Season season;

    @Column(name = "crop_name")
    private  String cropName;

    @Column(name = "expected_amount(Tons)")
    private double expectedAmount;

}
