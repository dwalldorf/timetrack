package com.dwalldorf.timetrack.rest.dto;

import java.io.Serializable;

public class VersionDto implements Serializable {

    private String version;

    public VersionDto() {
    }

    public VersionDto(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    public VersionDto setVersion(String version) {
        this.version = version;
        return this;
    }
}