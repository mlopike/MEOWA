package com.bsac.meowa.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bsac.meowa.model.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {
}