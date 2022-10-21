package com.differentdoors.hubspot.models.Search;

import java.util.List;

public class Search {
    private List<Filter> filters;

    public Search(List<Filter> filters) {
        this.filters = filters;
    }

    public List<Filter> getFilters() {
        return filters;
    }

    public void setFilters(List<Filter> filters) {
        this.filters = filters;
    }
}
