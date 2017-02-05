package com.dwalldorf.timetrack.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.List;

public class ListDto<T extends Serializable> implements Serializable {

    private List<T> items;

    public ListDto(List<T> items) {
        this.items = items;
    }

    @JsonProperty
    public List<T> getItems() {
        return items;
    }

    @JsonProperty
    public int getCount() {
        if (items != null) {
            return items.size();
        }
        return 0;
    }
}