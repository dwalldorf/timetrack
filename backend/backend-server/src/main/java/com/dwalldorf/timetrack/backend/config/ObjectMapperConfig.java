package com.dwalldorf.timetrack.backend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperConfig {

    private ObjectMapper mapper;

    @Bean
    public ObjectMapper objectMapper() {
        if (mapper == null) {
            this.mapper = new ObjectMapper();
            this.mapper.registerModule(new JodaModule());
        }

        return mapper;
    }
}