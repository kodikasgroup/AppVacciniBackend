package com.kodikas.appvaccinibackend.repository;

import com.kodikas.appvaccinibackend.model.Citizen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitizenRepository extends JpaRepository<Citizen, String> {
}
