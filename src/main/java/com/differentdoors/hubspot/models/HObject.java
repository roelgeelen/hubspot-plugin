package com.differentdoors.hubspot.models;

import com.differentdoors.hubspot.models.Objects.Company;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class HObject<T> {
    // Customer
    private String vid;
    @JsonProperty("associated-company")
    private HObject<Company<Property>> company;

    private String id;
    private String objectId;
    private T properties;

    public HObject(T properties) {
        this.properties = properties;
    }
}
