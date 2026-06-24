package com.bsac.meowa.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Location")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private Integer locationId;

    @Column(name = "name")
    private String name;

    @Column(name = "long", precision = 9, scale = 6)
    private BigDecimal lon;

    @Column(name = "lat", precision = 9, scale = 6)
    private BigDecimal lat;
}
