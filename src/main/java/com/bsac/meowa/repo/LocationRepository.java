package com.bsac.meowa.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bsac.meowa.model.Location;

public interface LocationRepository extends JpaRepository<Location, Integer> {
}