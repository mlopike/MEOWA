package com.bsac.meowa.repo;

import com.bsac.meowa.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, Integer> {
    Optional<Country> findByCountryCode(String countryCode);
}