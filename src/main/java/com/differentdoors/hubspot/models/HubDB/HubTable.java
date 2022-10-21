package com.differentdoors.hubspot.models.HubDB;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class HubTable<T> {
    private String id;
    private T values;
    private String path;
    private String name;
    private String childTableId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public T getValues() {
        return values;
    }

    public void setValues(T values) {
        this.values = values;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChildTableId() {
        return childTableId;
    }

    public void setChildTableId(String childTableId) {
        this.childTableId = childTableId;
    }

}