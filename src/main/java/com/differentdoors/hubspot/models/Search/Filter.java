package com.differentdoors.hubspot.models.Search;

public class Filter {
    private String propertyName;
    private String operator;
    private String value;

    public Filter(String propertyName, String operator, String value) {
        this.propertyName = propertyName;
        this.operator = operator;
        this.value = value;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
