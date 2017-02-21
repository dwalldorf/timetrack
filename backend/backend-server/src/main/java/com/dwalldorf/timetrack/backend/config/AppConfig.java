package com.dwalldorf.timetrack.backend.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@ComponentScan({"com.dwalldorf.timetrack.repository"})
@EnableAsync
public class AppConfig {
}