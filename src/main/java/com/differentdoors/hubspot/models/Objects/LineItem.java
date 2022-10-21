package com.differentdoors.hubspot.models.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LineItem {
    private String name;
    private String hs_product_id;
    private String price;
    private String quantity;
    private String hs_position_on_quote;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHs_product_id() {
        return hs_product_id;
    }

    public void setHs_product_id(String hs_product_id) {
        this.hs_product_id = hs_product_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getHs_position_on_quote() {
        return hs_position_on_quote;
    }

    public void setHs_position_on_quote(String hs_position_on_quote) {
        this.hs_position_on_quote = hs_position_on_quote;
    }
}
