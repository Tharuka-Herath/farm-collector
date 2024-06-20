package com.example.farmcollector.model;

import com.example.farmcollector.enums.Season;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;


@Entity
@Table(name = "crop", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"crop_type", "season_name", "yield_year", "farm_id", "farmer_id"})
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Crop {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "crop_id")
    private String cropId;

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
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "farmer_id")
    private Long farmerId;

    @Column(name = "farm_id")
    private Long farmId;

}
