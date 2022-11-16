package com.differentdoors.hubspot.models.HubDB;

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
public class TableConfig {
    private List<Configuration> configuraties;
    private String deal_id;

    public void addConfiguration(String configId) {
        this.configuraties.add(new Configuration(configId));
    }

    public void removeConfiguration(String configId) {
        configuraties = configuraties.stream().filter(c -> !Objects.equals(c.getId(), configId)).collect(Collectors.toList());
    }
}
