package com.dwalldorf.timetrack.backend.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@ComponentScan({
        "com.dwalldorf.timetrack.backend",
        "com.dwalldorf.timetrack.repository"
})
@EnableAutoConfiguration
@EnableAsync
public class AppConfig {
}
