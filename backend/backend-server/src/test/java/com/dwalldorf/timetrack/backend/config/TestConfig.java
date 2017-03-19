package com.dwalldorf.timetrack.backend.config;

import static org.mockito.Mockito.*;

import com.dwalldorf.timetrack.repository.repository.UserRepository;
import com.dwalldorf.timetrack.repository.repository.WorklogDayMetricRepository;
import com.dwalldorf.timetrack.repository.repository.WorklogRepository;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import javax.servlet.http.HttpSession;
import org.mockito.Mockito;
import org.mongodb.morphia.Datastore;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.session.SessionAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.mock.web.MockHttpSession;

@Configuration
@SpringBootApplication(exclude = {
        AppConfig.class,
        SessionRepositoryConfig.class,

        MongoAutoConfiguration.class,
        MongoDataAutoConfiguration.class,
        MongoRepositoriesAutoConfiguration.class,
        SessionAutoConfiguration.class,
        RedisAutoConfiguration.class,
        RedisRepositoriesAutoConfiguration.class
}, scanBasePackages = {
        "com.dwalldorf.timetrack.backend",
        "com.dwalldorf.timetrack.repository"
})
@PropertySource("classpath:application.properties")
public class TestConfig {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private WorklogRepository worklogRepository;

    @MockBean
    private WorklogDayMetricRepository worklogDayMetricRepository;

    @Bean
    @Primary
    public HttpSession httpSession() {
        return new MockHttpSession();
    }

    @Bean
    @Primary
    public Mongo mongo() throws Exception {
        return mock(MongoClient.class);
    }

    @Bean
    @Primary
    public MongoClient mongoClient() throws Exception {
        return (MongoClient) mongo();
    }

    @Bean
    @Primary
    public Datastore datastore() throws Exception {
        return mock(Datastore.class);
    }

    @Bean
    @Primary
    public JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory factoryMock = Mockito.mock(JedisConnectionFactory.class);
        JedisConnection connection = Mockito.mock(JedisConnection.class);
        Mockito.when(factoryMock.getConnection()).thenReturn(connection);

        return factoryMock;
    }
}