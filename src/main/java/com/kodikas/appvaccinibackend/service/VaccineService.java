package com.kodikas.appvaccinibackend.service;

import com.kodikas.appvaccinibackend.model.VaccinationCampaign;
import com.kodikas.appvaccinibackend.model.Vaccine;
import com.kodikas.appvaccinibackend.repository.VaccineRepository;
import com.kodikas.appvaccinibackend.wrapper.VaccinationCampaignWrapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class VaccineService {
    private final VaccineRepository vaccineRepository;

    public List<Vaccine> getVaccines() {
        return vaccineRepository.findAll();
    }

    public Vaccine addVaccine(Vaccine vaccine) {
        return vaccineRepository.save(vaccine);
    }

    public Vaccine addQuantity(Long vaccineID, Long quantity) {
        Optional<Vaccine> optionalVaccine = vaccineRepository.findById(vaccineID);
        Vaccine vaccine;
        if (optionalVaccine.isPresent())
            vaccine = optionalVaccine.get();
        else
            throw new IllegalStateException("Insert a Valid ID");

        if (quantity > vaccine.getQuantity())
            throw new IllegalStateException("Insert a Valid quantity");

        vaccine.setQuantity(vaccine.getQuantity() + quantity);
        return vaccineRepository.save(vaccine);
    }

    public Vaccine getbyId (Long vaccineID){
        Optional<Vaccine> optionalVaccine = vaccineRepository.findById(vaccineID);
        if (optionalVaccine.isPresent())
            return optionalVaccine.get();
        else
            throw new IllegalStateException("Insert a Valid ID");
    }

    public VaccinationCampaign getVaccinationCampaign (Long vaccineID){
        Vaccine vaccine= vaccineRepository.findByVaccineID(vaccineID);
        HashSet<VaccinationCampaign> campaigns = new HashSet<>();
        if (vaccine != null){
            return vaccine.getVaccinationCampaign() ;
        }
        else
            throw new IllegalStateException("Insert a Valid ID");
    }
}
