package com.bsac.meowa.repo;

import com.bsac.meowa.model.Source;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SourceRepository extends JpaRepository<Source, Integer> {
    Optional<Source> findBySourceName(String sourceName);
}