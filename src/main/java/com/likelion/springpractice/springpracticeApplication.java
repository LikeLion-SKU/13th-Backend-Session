package com.likelion.springpractice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class springpracticeApplication {

    public static void main(String[] args) {
        SpringApplication.run(springpracticeApplication.class, args);
    }

}
