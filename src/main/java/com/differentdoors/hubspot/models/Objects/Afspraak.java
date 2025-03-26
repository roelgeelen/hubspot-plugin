package com.differentdoors.hubspot.models.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Afspraak<T> {
    private T afspraak_name;
    private T afspraak_datum;
    private T afspraak_tijd;
    private T afspraak_locatie;
    private T dimensie;
    private T geannuleerd_op;
    private T notitie;
    private T hubspot_owner_id;
    private T reden_van_annuleren;
    private T soort_afspraak;
    private T status;
    private T hs_createdate;
    private T created_by;
}
