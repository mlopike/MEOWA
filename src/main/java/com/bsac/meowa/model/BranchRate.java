package com.bsac.meowa.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "branch_rates")
@Data
public class BranchRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("filial_id")
    @Column(name = "fil_id")
    private String filId;

    @JsonProperty("name")
    private String city;

    @JsonProperty("filials_text")
    @Column(name = "br_name")
    private String brName;

    @JsonProperty("street")
    private String str;

    @JsonProperty("home_number")
    private String num;

    @JsonProperty("USD_in")
    @Column(name = "usd_in")
    private Double usdIn;

    @JsonProperty("USD_out")
    @Column(name = "usd_out")
    private Double usdOut;

    @JsonProperty("EUR_in")
    @Column(name = "eur_in")
    private Double eurIn;

    @JsonProperty("EUR_out")
    @Column(name = "eur_out")
    private Double eurOut;

    @JsonProperty("RUB_in")
    @Column(name = "rub_in")
    private Double rubIn;

    @JsonProperty("RUB_out")
    @Column(name = "rub_out")
    private Double rubOut;

    private LocalDateTime time;
}