package com.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan("com.project.security")
@ComponentScan("com.project.controller")
@SpringBootApplication
public class StartRESTServices {
    public static void main(String[] args) {
        SpringApplication.run(StartRESTServices.class);
    }
}
