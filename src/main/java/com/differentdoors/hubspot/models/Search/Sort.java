package com.differentdoors.hubspot.models.Search;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Sort {
    private String propertyName;
    private String direction;

    public Sort(String propertyName, String direction) {
        this.propertyName = propertyName;
        this.direction = direction;
    }
}
