package com.dwalldorf.timetrack.backend.rest.controller;

import com.dwalldorf.timetrack.backend.rest.dto.VersionDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(VersionController.BASE_URI)
public class VersionController {

    public static final String BASE_URI = "/version";

    @Value("${app.version}")
    private String version;

    @GetMapping
    public VersionDto getVersion() {
        return new VersionDto(this.version);
    }
}