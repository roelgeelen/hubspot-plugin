package com.differentdoors.hubspot.models.HubDB;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TableConfig {
    //private List<Configuration> configuraties;
    private String deal_id;

//    public void addConfiguration(String configId) {
//        this.configuraties.add(new Configuration(configId));
//    }
//
//    public void removeConfiguration(String configId) {
//        configuraties = configuraties.stream().filter(c -> !Objects.equals(c.getId(), configId)).collect(Collectors.toList());
//    }
}
