package com.bsac.meowa.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bsac.meowa.model.BranchRate;

public interface BranchRateRepository extends JpaRepository<BranchRate, Integer> {
}