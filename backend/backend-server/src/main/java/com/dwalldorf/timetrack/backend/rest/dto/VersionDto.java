package com.dwalldorf.timetrack.backend.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

public class VersionDto implements Serializable {

    private String version;

    public VersionDto(String version) {
        this.version = version;
    }

    @JsonProperty
    public String getVersion() {
        return version;
    }
}