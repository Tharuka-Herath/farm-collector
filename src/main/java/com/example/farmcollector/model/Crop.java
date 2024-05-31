package com.example.farmcollector.model;

import com.example.farmcollector.enums.Season;
import com.example.farmcollector.util.IdGenerator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Crop {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "crop_id")
    private String cropId= IdGenerator.generateCropId();

    @Column(name = "crop_type")
    private String cropType;

    @Enumerated(EnumType.STRING)
    @Column(name = "season_name")
    private Season season;

    @Column(name = "yield_year")
    private Integer yieldYear;

    @Column(name = "expected_amount")
    private Double expectedAmount;

    @Column(name = "actual_amount")
    private Double actualAmount;

    @CreationTimestamp
    @Column(name = "created_at",updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    @JsonIgnore
    @ManyToMany(mappedBy = "crops")
    private Set<Farm> farms = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "farmer_id", unique = true)
    private Farmer farmer;

}
