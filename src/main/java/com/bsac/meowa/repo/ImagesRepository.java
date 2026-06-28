package com.bsac.meowa.repo;

import com.bsac.meowa.model.Images;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ImagesRepository extends JpaRepository<Images, Integer> {
    List<Images> findByNewsId(Integer newsId);
}