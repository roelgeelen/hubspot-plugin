package com.differentdoors.hubspot.models.Search;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Filter {
    private String propertyName;
    private String operator;
    private String value;
    private List<String> values;

    public Filter(String propertyName, String operator, String value) {
        this.propertyName = propertyName;
        this.operator = operator;
        this.value = value;
    }

    public Filter(String propertyName, String operator, List<String> values) {
        this.propertyName = propertyName;
        this.operator = operator;
        this.values = values;
    }
}
