package com.nidhallourimi.springsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.nidhallourimi.springsecurity.auth","com.nidhallourimi.springsecurity.security","com.nidhallourimi.springsecurity.controller","com.nidhallourimi.springsecurity.student"})
public class SpringSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityApplication.class, args);
    }

}
