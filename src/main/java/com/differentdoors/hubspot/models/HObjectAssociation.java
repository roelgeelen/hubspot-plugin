package com.differentdoors.hubspot.models;

import com.differentdoors.hubspot.models.Objects.Association;
import com.differentdoors.hubspot.models.Objects.AssociationType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class HObjectAssociation {
    private Association to;
    private List<AssociationType> types;
}
