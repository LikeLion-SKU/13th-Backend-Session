package com.likelion.springpractice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing    // BaseTimeEntity에서 생성일시, 수정일시 위해서 필요
public class springpracticeApplication {

  public static void main(String[] args) {
    SpringApplication.run(springpracticeApplication.class, args);
  }

}
