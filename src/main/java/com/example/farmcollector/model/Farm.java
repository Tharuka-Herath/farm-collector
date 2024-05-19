package com.example.farmcollector.model;

import com.example.farmcollector.enums.Season;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "farm")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Farm {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long farmId;

    @Column(name = "farm_name")
    private String farmName;

    @Column(name = "farm_area(in_acres)")
    private Double farmArea;

    @Column(name="season_name")
    private Season season;

    @Column(name = "year")
    private Integer yieldYear;



}
