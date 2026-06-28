package com.bsac.meowa.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Country")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer countryId;

    private String countryCode;
    private String countryName;
}