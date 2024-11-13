package com.yoyomo.global.config.mongo;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories({"com.yoyomo.domain.form.domain.repository", "com.yoyomo.domain.application.domain.repository.mongo"})
public class MongoConfig {
}
