package com.bsac.meowa.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Source")
public class Source {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sourceId;

    private String sourceName;
    private String sourceIcon;
    private String sourceUrl;
}