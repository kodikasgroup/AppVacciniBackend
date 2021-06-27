package com.kodikas.appvaccinibackend.repository;

import com.kodikas.appvaccinibackend.model.Availability;
import com.kodikas.appvaccinibackend.wrapper.VaccineIdWrapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
@ExtendWith(SpringExtension.class)
@DataJpaTest
class AvailabilityRepositoryTest {
    @Autowired private AvailabilityRepository underTest;
    Availability disponibilita;
    Availability disponibilita2;
    String nomeAmbulatorio;
    Long idVaccino;
    String categoria;
    VaccineIdWrapper vaccineWrapper;

    @Test
    void injectedComponentsAreNotNull(){
        assertThat(underTest).isNotNull();
    }

    @BeforeEach
    void setUp() {
        nomeAmbulatorio = "Golosine";
        idVaccino = 1L;
        categoria = "over80";

        disponibilita = new Availability(
                nomeAmbulatorio,idVaccino,
                LocalDate.of(2021,05,06),LocalDate.of(2021,05,21),
                LocalTime.of(9,00),LocalTime.of(12,00));
        disponibilita2 = new Availability(
                "SantaLucia",2L,
                LocalDate.of(2021,05,02),LocalDate.of(2021,05,25),
                LocalTime.of(11,00),LocalTime.of(13,00));
    }

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void findAllById_IdVaccino_itShoudRetrieve() {
        underTest.save(disponibilita);
        List<Availability> result = underTest.findAllByAvailabilityId_IdVaccine(idVaccino);
        assertThat(result.isEmpty()).isFalse();
    }

    @Test
    void findAllById_IdVaccino_itShouldRetrivedcorrect() {
        underTest.save(disponibilita);
        List<Availability> result = underTest.findAllByAvailabilityId_IdVaccine(idVaccino);
        boolean test = true;
        for (Availability find : result){
            if (!(find.getAvailabilityId().getIdVaccine() == disponibilita.getAvailabilityId().getIdVaccine())) {
                test = false;
            }
        }
        assertThat(test).isTrue();
    }


    @Test
    void findAllById_IdVaccino_itShouldreturnsonlythecorrectvalues() {
        underTest.save(disponibilita);
        underTest.save(disponibilita2);
        List<Availability> result = underTest.findAllByAvailabilityId_IdVaccine(idVaccino);
        boolean test = true;
        for (Availability find : result){
            if (!(find.getAvailabilityId().getIdVaccine() == disponibilita.getAvailabilityId().getIdVaccine())) {
                test = false;
            }
        }
        assertThat(test).isTrue();
    }
}