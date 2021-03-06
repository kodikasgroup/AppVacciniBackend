package com.kodikas.appvaccinibackend.controller;

import com.kodikas.appvaccinibackend.model.Reservation;
import com.kodikas.appvaccinibackend.service.ReservationService;
import com.kodikas.appvaccinibackend.wrapper.ReservationWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservationControllerUnitTest {

    @Mock
    private ReservationService reservationService;
    private ReservationController underTest;
    private Reservation entry1;
    private Reservation entry2;

    @BeforeEach
    void setUp() {

        underTest = new ReservationController(reservationService);
        entry1 = new Reservation(2L,"GRRDFN68H68L414I","Fiera",
                LocalDate.of(2021,05,20), LocalTime.of(13,0));

        entry2 = new Reservation(25L,"FRRFTH32C49L058J","Golosine",
                LocalDate.of(2021,05,21), LocalTime.of(13,0));
    }

    @Test
    void getAllReservations() {
        List<Reservation> reservation_list = List.of(entry1, entry2);

        when(reservationService.getAllReservations()).thenReturn(reservation_list);

        ReservationWrapper result = underTest.getAllReservations();

        verify(reservationService).getAllReservations();

        assertEquals(result.getReservations(), reservation_list);

    }

    @Test
    void addReservation() {

        when(reservationService.addReservation(entry1)).thenReturn(entry1);

        Reservation result = underTest.addReservation(entry1);

        verify(reservationService).addReservation(entry1);

        assertEquals(entry1, result);
    }

    @Test
    void getReservationsByFiscalCode() {
        List<Reservation> reservations_list = List.of(entry2);
        String ficalcode = "FRRFTH32C49L058J";

        when(reservationService.getReservation(ficalcode)).thenReturn(reservations_list);

        ReservationWrapper result = underTest.getReservationsByFiscalCode(ficalcode);
        boolean check = true;

        for (Reservation find : result.getReservations()) {
            if (!(find.getReservationId().getFiscalCode().equals(ficalcode))) {
                check = false;
                break;
            }
        }

        assertThat(check).isTrue();
    }
}