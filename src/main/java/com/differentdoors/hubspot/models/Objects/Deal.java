package com.differentdoors.hubspot.models.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Deal<T> {
    private T dealname;
    private T fase_project;
    private T verwachte_leverweek_klant;
    private T verwachte_leverjaar_klant;
    private T type_woning;
    private T beide_personen_aanwezig_bij_gesprek_;
    private T wanneer_gaat_het_project_spelen_;
    private T dagdeel_bezoek;
    private T deur_bestellen;
    private T status_project;
    private T situatie;
    private T garage_aangrenzend_aan_woning;
    private T leeftijdsschatting;
    private T thuiswonende_kinderen;
    private T wie_is_er_bij_het_gesprek_aanwezig;
    private T geschatte_woningwaarde;
    private T materiaal_te_bestellen_door_werkvoorbereiding;
    private T afspraak_datum;
    private T dealtype;
    private T tijd_van_de_afspraak;
    private T hubspot_owner_id;
    private T locatie_van_de_afspraak;
    private T dealstage;
    private T signature;
    private T amount;
    private T project_ingemeten;
}
