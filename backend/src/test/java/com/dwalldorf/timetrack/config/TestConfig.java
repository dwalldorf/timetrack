package com.dwalldorf.timetrack.config;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import javax.servlet.http.HttpSession;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.session.SessionAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mock.web.MockHttpSession;

@Configuration
@SpringBootApplication(exclude = {
        MongoConfig.class,

        MongoAutoConfiguration.class,
        MongoDataAutoConfiguration.class,
        SessionAutoConfiguration.class,
        RedisAutoConfiguration.class,
        RedisRepositoriesAutoConfiguration.class,
}, scanBasePackages = {"com.dwalldorf.timetrack"})
@PropertySource("classpath:application.properties")
public class TestConfig {

    @Bean
    @Primary
    public HttpSession httpSession() {
        return new MockHttpSession();
    }

    @Bean
    @Primary
    public Mongo mongo() throws Exception {
        return Mockito.mock(MongoClient.class);
    }

    @Bean
    @Primary
    public MongoClient mongoClient() throws Exception {
        return (MongoClient) mongo();
    }
}
