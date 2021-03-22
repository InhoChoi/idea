package com.inhochoi.idea;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class IdeaApplication {
    public static void main(String[] args) {
        SpringApplication.run(IdeaApplication.class, args);
    }
}
