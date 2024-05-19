package com.example.farmcollector.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "farmer")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Farmer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long farmerId;

    @Column(name = "farmer_name")
    private String farmerName;

}
