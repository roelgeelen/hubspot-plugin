package com.differentdoors.hubspot.models.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Note {
    private String hs_timestamp;
    private String hs_note_body;

    public String getHs_timestamp() {
        return hs_timestamp;
    }

    public void setHs_timestamp(String hs_timestamp) {
        this.hs_timestamp = hs_timestamp;
    }

    public String getHs_note_body() {
        return hs_note_body;
    }

    public void setHs_note_body(String hs_note_body) {
        this.hs_note_body = hs_note_body;
    }
}
