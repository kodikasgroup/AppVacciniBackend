package com.kodikas.appvaccinibackend.service;

import com.kodikas.appvaccinibackend.model.Reservation;
import com.kodikas.appvaccinibackend.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;
    private ReservationService underTest;
    private Reservation entry1;
    private Reservation entry2;

    @BeforeEach
    void setUp() {
        underTest = new ReservationService(reservationRepository);
        entry1 = new Reservation("GRRDFN68H68L414I","Fiera",2L,
                LocalDate.of(2021,05,20).atTime(13,0));

        entry2 = new Reservation("FRRFTH32C49L058J","Golosine",25L,
                LocalDate.of(2021,05,21).atTime(9,0));
    }

    @Test
    void getAllReservations() {
        List<Reservation> reservation_list = List.of(entry1,entry2);

        when(reservationRepository.findAll()).thenReturn(reservation_list);

        underTest.getAllReservations();

        verify(reservationRepository).findAll();
    }

    @Test
    void addReservation() {
        underTest.addReservation(entry1);

        verify(reservationRepository).save(entry1);
    }

    @Test
    void getReservation_shouldcorrectlyreturns() {
        List<Reservation> reservation_list = List.of(entry1);
        String fiscalcode = "GRRDFN68H68L414I";

        when(reservationRepository.findAllByFiscalcode(fiscalcode)).thenReturn(reservation_list);

        List<Reservation>result = underTest.getReservation(fiscalcode);

        boolean check = false;

        for (Reservation find : result){
            if(!(find.getFiscalcode().equals(fiscalcode))){
                check = true;
            }
        }

        assertThat(check).isFalse();
    }

    @Test
    void getReservation_shouldThrowErrorWhenfindsnothing_(){
        String fiscalcode = "GRRDFN68H68L414I";
        assertThatThrownBy(
                ()-> underTest.getReservation(fiscalcode)
        ).isInstanceOf(IllegalStateException.class).hasMessage("I have not found any reservations for this fiscalcode");

        verify(reservationRepository).findAllByFiscalcode(any());
    }
}