package com.example.farmcollector.model;

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
@Table(name = "farmer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Farmer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "farmer_id")
    private String farmerId = IdGenerator.generateFarmerId();

    @Column(name = "farmer_name")
    private String farmerName;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    @JsonIgnore
    @ManyToMany(mappedBy = "farmers")
    private Set<Farm> farms = new HashSet<>();

    @JsonIgnore
    @OneToOne(mappedBy = "farmer")
    private Crop crop;

}
