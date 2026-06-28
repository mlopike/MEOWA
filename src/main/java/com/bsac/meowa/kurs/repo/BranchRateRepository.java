package com.bsac.meowa.kurs.repo;

import com.bsac.meowa.kurs.model.BranchRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchRateRepository extends JpaRepository<BranchRate, Long> {
}