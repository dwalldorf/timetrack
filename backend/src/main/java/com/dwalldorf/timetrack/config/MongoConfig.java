
package com.dwalldorf.timetrack.config;

import com.dwalldorf.timetrack.annotation.Log;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

@Configuration
public class MongoConfig extends AbstractMongoConfiguration {

    @Log
    private Logger logger;

    @Value("${mongo.host}")
    private String host;

    @Value("${mongo.db}")
    private String db;

    @Value("${mongo.port}")
    private Integer port;

    @Override
    protected String getDatabaseName() {
        return db;
    }

    @Bean
    @Override
    public Mongo mongo() throws Exception {
        return new MongoClient(host, port);
    }

    @Bean
    public MongoClient mongoClient() throws Exception {
        return (MongoClient) mongo();
    }

    @Bean
    public Datastore datastore() throws Exception {
        Morphia morphia = new Morphia();

        Datastore datastore = morphia.createDatastore(mongoClient(), db);
        datastore.ensureIndexes();

        logger.info("created mongo indexes");

        return datastore;
    }
}
