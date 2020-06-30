package com.demo;

import com.mongodb.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import java.net.UnknownHostException;

@Configuration
@PropertySource(value = "classpath:/mongo.properties")
@Profile({"default"})
@EnableMongoAuditing
public class MongoConfig {
    @Autowired
    Environment env;

    @Bean
    public MongoClient mongoClient() throws UnknownHostException {
        MongoClientOptions.Builder builder = new MongoClientOptions.Builder();
        builder.connectionsPerHost(50);
        builder.writeConcern(WriteConcern.JOURNALED);
        builder.readPreference(ReadPreference.secondaryPreferred());
        MongoClientOptions options = builder.build();
        MongoClient mongoClient = new MongoClient(new ServerAddress(env.getProperty("mongo.server"), Integer.parseInt(env.getProperty("mongo.port"))), options);
        return mongoClient;
    }

    @Bean
    public MongoDbFactory mongoDbFactory() throws UnknownHostException {
        MongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(mongoClient(),
                env.getProperty("mongo.databaseName"));
       /* MongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(mongoClient(),
                env.getProperty("mongo.databaseName"),
                new UserCredentials(env.getProperty("mongo.userName"),
                        env.getProperty("mongo.password")));*/
        return mongoDbFactory;
    }

    @Bean
    public MongoTemplate mongoTemplate() throws UnknownHostException {
        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());
        return mongoTemplate;
    }

}
