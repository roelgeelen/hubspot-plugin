package com.differentdoors.hubspot.models.HubDB;

public class Configuration {
    private String id;
    private String type;

    public Configuration(String id) {
        this.id = id;
        this.type = "foreignid";
    }

    public Configuration() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
