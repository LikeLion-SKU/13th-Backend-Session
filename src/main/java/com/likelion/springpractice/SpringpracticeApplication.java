package com.likelion.springpractice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpringpracticeApplication {

  public static void main(String[] args) {
    System.out.println("애플리케이션 시작 중...");
    SpringApplication.run(SpringpracticeApplication.class, args);

  }

}
