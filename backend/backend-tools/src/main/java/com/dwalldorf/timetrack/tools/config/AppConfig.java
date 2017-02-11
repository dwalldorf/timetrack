package com.dwalldorf.timetrack.tools.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value = {
        "com.dwalldorf.timetrack.model",
        "com.dwalldorf.timetrack.repository"
})
public class AppConfig {
}