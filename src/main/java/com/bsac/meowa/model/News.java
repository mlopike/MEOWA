package com.bsac.meowa.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "News")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer newsId;

    @Column(name = "external_id", unique = true)
    private String externalId;

    @Column(name = "article_url")
    private String articleUrl;

    @Column(name = "source_id")
    private Integer sourceId;

    @Column(name = "country_id")
    private Integer countryId;

    private String title;
    private String content;
    private String category;

    @Column(name = "pub_t")
    private LocalDateTime pubT;

    @Column(name = "add_t")
    private LocalDateTime addT;

    @Column(name = "upd_t")
    private LocalDateTime updT;

    @Column(name = "del_t")
    private LocalDateTime delT;
}