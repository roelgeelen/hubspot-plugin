package com.differentdoors.hubspot.models.HubDB;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TableConfig {
    private List<Configuration> configuraties;
    private String deal_id;

    public List<Configuration> getConfiguraties() {
        return configuraties;
    }

    public void setConfiguraties(List<Configuration> configuraties) {
        this.configuraties = configuraties;
    }

    public String getDeal_id() {
        return deal_id;
    }

    public void setDeal_id(String deal_id) {
        this.deal_id = deal_id;
    }

    public void addConfiguration(String configId) {
        this.configuraties.add(new Configuration(configId));
    }

    public void removeConfiguration(String configId) {
        configuraties = configuraties.stream().filter(c -> !Objects.equals(c.getId(), configId)).collect(Collectors.toList());
    }
}
