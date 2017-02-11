package com.dwalldorf.timetrack.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.dwalldorf.timetrack")
public class WebApplication {

    public static void main(String args[]) {
        SpringApplication.run(WebApplication.class, args);
    }
}