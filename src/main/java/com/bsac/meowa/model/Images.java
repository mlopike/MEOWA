package com.bsac.meowa.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Images")
public class Images {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer imageId;

    @Column(name = "news_id")
    private Integer newsId;

    private String imageUrl;

    @Column(name = "add_t")
    private LocalDateTime addT;

    @Column(name = "upd_t")
    private LocalDateTime updT;

    @Column(name = "del_t")
    private LocalDateTime delT;
}