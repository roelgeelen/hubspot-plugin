package com.differentdoors.hubspot.models.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LineItem {
    private String name;
    private String hs_product_id;
    private String price;
    private String amount;
    private String quantity;
    private String hs_position_on_quote;
    private String hs_product_type;
    private String configuration_name;
    private String configuration_id;
    private String bc_job_task_no;
    private String bc_no;
    private String bc_type;
    private String bc_quantity;
    private String hs_cost_of_goods_sold;
    private Integer weging;
}
