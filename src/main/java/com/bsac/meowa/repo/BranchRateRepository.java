package com.bsac.meowa.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bsac.meowa.model.BranchRate;

@Repository
public interface BranchRateRepository extends JpaRepository<BranchRate, Long> {
}