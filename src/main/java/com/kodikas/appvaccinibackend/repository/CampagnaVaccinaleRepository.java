package com.kodikas.appvaccinibackend.repository;

import com.kodikas.appvaccinibackend.model.CampagnaVaccinale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampagnaVaccinaleRepository extends JpaRepository<CampagnaVaccinale, Long> {
}