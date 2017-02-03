package com.dwalldorf.timetrack.rest.controller;

import com.dwalldorf.timetrack.rest.dto.VersionDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersionController {

    @Value("${app.version}")
    private String version;

    @GetMapping("/version")
    public VersionDto getVersion() {
        return new VersionDto(this.version);
    }
}