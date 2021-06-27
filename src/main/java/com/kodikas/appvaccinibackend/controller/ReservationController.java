package com.kodikas.appvaccinibackend.controller;

import com.kodikas.appvaccinibackend.model.Reservation;
import com.kodikas.appvaccinibackend.service.ReservationService;
import com.kodikas.appvaccinibackend.wrapper.ReservationWrapper;
import com.kodikas.appvaccinibackend.wrapper.VaccineIdWrapper;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Wrapper;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/reservations")
public class ReservationController {

    private final ReservationService reservationService ;

    @GetMapping
    public ReservationWrapper getAllReservations (){
        return new ReservationWrapper(reservationService.getAllReservations());
    }

    @PostMapping
    public Reservation addReservation(@RequestBody Reservation reservation){
        return reservationService.addReservation(reservation);
    }

    @GetMapping("/{fiscalCode}")
    public ReservationWrapper getReservationsByFiscalCode(@PathVariable String fiscalCode){
        return new ReservationWrapper(reservationService.getReservation(fiscalCode));
    }

    @GetMapping("/clinicName/{clinicName}/idVaccine/{idVaccine}/date/{date}")
    public ReservationWrapper getReservationsByDate(@PathVariable String clinicName , @PathVariable Long idVaccine, @PathVariable String date) throws UnsupportedEncodingException {
        //convert String to LocalDate
        LocalDate localDate = LocalDate.parse(date);
        String clinicNameDecode = URLDecoder.decode(clinicName,"UTF-8");

       // System.out.println(">>>>>>MEGA TEST:"+ "\n" + clinicNameDecode +idVaccine +localDate);
        
        return new ReservationWrapper(reservationService.getReservationbyDate(clinicNameDecode,idVaccine,localDate));
    }

    @GetMapping("/fiscalcode/{fiscalcode}/idvaccine/{idVaccine}")
    public ReservationWrapper getReservationsByfiscalcodeandVaccine(@PathVariable String fiscalcode ,@PathVariable VaccineIdWrapper idVaccine){
        return new ReservationWrapper(reservationService.getReservationbyFiscalCode(fiscalcode,idVaccine));
    }

}