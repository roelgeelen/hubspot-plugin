package com.differentdoors.hubspot.models.HubDB;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Configuration {
    private String id;
    private String type;

    public Configuration(String id) {
        this.id = id;
        this.type = "foreignid";
    }
}
