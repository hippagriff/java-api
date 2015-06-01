package com.lumira;

import org.jsondoc.spring.boot.starter.EnableJSONDoc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableJSONDoc
public class TokenServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TokenServerApplication.class, args);
    }
}
