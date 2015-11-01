package edu.sjsu.cmpe275.lab2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

//@SpringBootApplication
@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Lab2MvcRestOrmAndTransactionsApplication {

    public static void main(String[] args) {
        SpringApplication.run(Lab2MvcRestOrmAndTransactionsApplication.class, args);
    }
}
