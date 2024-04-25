package com.differentdoors.hubspot.models.Search;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Search {
    private List<Filter> filters;
    private List<FilterGroup> filterGroups;
    private List<String> properties;
    private String limit;
    private String after;

    public Search(List<?> filters, List<String> properties) {
        if (filters != null && !filters.isEmpty()) {
            if (filters.get(0) instanceof Filter) {
                this.filters = castToFiltersList(filters);
            } else if (filters.get(0) instanceof FilterGroup) {
                this.filterGroups = castToFilterGroupList(filters);
            }
        }
        this.properties = properties;
    }

    public Search(List<?> filters, List<String> properties, String limit, String after) {
        if (filters != null && !filters.isEmpty()) {
            if (filters.get(0) instanceof Filter) {
                this.filters = castToFiltersList(filters);
            } else if (filters.get(0) instanceof FilterGroup) {
                this.filterGroups = castToFilterGroupList(filters);
            }
        }

        this.properties = properties;
        this.limit = limit;
        this.after = after;
    }

    public void addFilter(FilterGroup group) {
        if(filterGroups == null){
            filterGroups = new ArrayList<>();
        }
        filterGroups.add(group);
    }

    @SuppressWarnings("unchecked")
    private List<Filter> castToFiltersList(List<?> filters) {
        return (List<Filter>) filters;
    }

    @SuppressWarnings("unchecked")
    private List<FilterGroup> castToFilterGroupList(List<?> filters) {
        return (List<FilterGroup>) filters;
    }
}
