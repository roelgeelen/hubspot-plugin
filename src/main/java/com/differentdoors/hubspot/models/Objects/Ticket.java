package com.differentdoors.hubspot.models.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Ticket<T> {
    private T hs_object_id;
    private T content;
    private T hs_pipeline;
    private T hs_pipeline_stage;
    private T subject;
    private T serviceaanvraag_categorie;
    private T storage_location;
    private T spullen_bestellen;
    private T service_reden;
    private T leverancier;
    private T afspraakverzoek;
    private T bezoekreden;
    private T bezoek_tijd;
    private T bezoek_datum;
    private T vast_gepland;
    private T bezoek_adres;
    private T bezoek_plaats;
    private T bezoek_postcode;
    private T memo_monteurs;
    private T projectnummer;
}
