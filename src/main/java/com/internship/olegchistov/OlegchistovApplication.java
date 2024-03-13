package com.internship.olegchistov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
@EnableMongoAuditing
@EnableCaching
public class OlegchistovApplication {

    public static void main(String[] args) {
        SpringApplication.run(OlegchistovApplication.class, args);
    }
}
