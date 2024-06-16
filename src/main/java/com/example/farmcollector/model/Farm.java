package com.example.farmcollector.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


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
    private String farmId;

    @Column(name = "farm_name")
    private String farmName;

    @Column(name = "location")
    private String location;

    @Column(name = "farm_area(in_acres)")
    private Double farmArea;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "farm", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Farmer> farmers = new ArrayList<>();

}
