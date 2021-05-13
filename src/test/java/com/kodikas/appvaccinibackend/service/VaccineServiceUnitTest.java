package com.kodikas.appvaccinibackend.service;

import com.kodikas.appvaccinibackend.model.Vaccine;
import com.kodikas.appvaccinibackend.repository.VaccineRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VaccineServiceUnitTest {
    @Mock
    private VaccineRepository vaccineRepository;
    private VaccineService underTest;
    private Vaccine vaccine;

    @BeforeEach
    void setUp() {
        this.underTest = new VaccineService(vaccineRepository);
        this.vaccine = new Vaccine(
                8L,
                "jansen",
                100L
        );
    }


    @Test
    void canGetAllVaccini() {
        // given
        List<Vaccine> vaccini = List.of(
                vaccine
        );

        // when
        when(vaccineRepository.findAll()).thenReturn(vaccini);

        underTest.getVaccines();

        // then
        verify(vaccineRepository).findAll();
    }

    @Test
    void shouldAddVaccino() {
        // when
        underTest.addVaccine(vaccine);

        // then
        verify(vaccineRepository).save(vaccine);
    }

    @Test
    void shouldAddQuantity() {
        // when
        Long id = vaccine.getVaccineID();
        when(vaccineRepository.existsById(id)).thenReturn(true);
        when(vaccineRepository.findById(id)).thenReturn(Optional.of(vaccine));

        underTest.addQuantity(id, 50L);
        // then
        verify(vaccineRepository).save(any());
        assertThat(vaccine.getQuantity()).isEqualTo(150L);
    }

    @ParameterizedTest
    @ValueSource(longs = {-10, 0, Long.MIN_VALUE})
    void shouldThrowErrorWhenQuantitaLessThanOrEqualZero(Long quantità) {
        // when
        Long id = vaccine.getVaccineID();

        // then
        assertThatThrownBy(
                () -> underTest.addQuantity(id, quantità)
        ).isInstanceOf(IllegalStateException.class)
                .hasMessage("La quantità inserita non è valida");

        verify(vaccineRepository, never()).save(any());

    }

    @Test
    void shouldThrowErrorWhenIdDoesNotExist() {
        // when
        Long id = vaccine.getVaccineID();
        when(vaccineRepository.existsById(id)).thenReturn(false);

        // then
        assertThatThrownBy(
                () -> underTest.addQuantity(id, 50L)
        ).isInstanceOf(IllegalStateException.class)
                .hasMessage("Inserire un'id valido");

        verify(vaccineRepository, never()).save(any());

    }

}