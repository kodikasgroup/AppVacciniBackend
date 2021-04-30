package com.kodikas.appvaccinibackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Vaccino {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long idVaccino;
    private String nomeVaccino;
    private Long quantità;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "id_campagna",
            referencedColumnName = "idCampagna"
    )

    private CampagnaVaccinale campagnaVaccinale;
//    private AventiDiritto aventiDiritto;

    public Vaccino(String nomeVaccino, Long quantità) {
        this.nomeVaccino = nomeVaccino;
        this.quantità = quantità;
    }

    @JsonIgnore
    private CampagnaVaccinale getCampagnaVaccinale() {
        return campagnaVaccinale;
    }
}
