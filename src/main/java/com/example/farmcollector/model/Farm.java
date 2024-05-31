package com.example.farmcollector.model;

import com.example.farmcollector.util.IdGenerator;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "farm")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Farm {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "farm_id")
    private String farmId = IdGenerator.generateFarmId();

    @Column(name = "farm_name")
    private String farmName;

    @Column(name = "location")
    private String location;

    @Column(name = "farm_area(in_acres)")
    private Double farmArea;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    @ManyToMany
    @JoinTable(
            name = "Farm_Farmer",
            joinColumns = @JoinColumn(name = "farm_id"),
            inverseJoinColumns = @JoinColumn(name = "farmer_id")
    )
    private Set<Farmer> farmers = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "Farm_Crop",
            joinColumns = @JoinColumn(name = "farm_id"),
            inverseJoinColumns = @JoinColumn(name = "crop_id")
    )
    private Set<Crop> crops = new HashSet<>();

}
