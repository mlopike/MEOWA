package com.bsac.meowa.repo;

import com.bsac.meowa.model.News;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface NewsRepository extends JpaRepository<News, Integer> {
    boolean existsByExternalId(String externalId);
    List<News> findAllByOrderByPubTDesc();
    void deleteByPubTBefore(LocalDateTime date);
}