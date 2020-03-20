package com.course;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by apple on 2020/3/13.
 */
@EnableScheduling
@SpringBootApplication(exclude = MongoAutoConfiguration.class)
public class Application {

    public static void main(String[] args){
        SpringApplication.run (Application.class,args);

    }
}
