package com.likelion.springpractice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing //Spring Data JPA에서, Auditing(감시)기능을 활성화하는 어노테이션!! main에 꼭 달아줘야함!!
//@CreatedDate, @LastModifiedDate가 작동하려면 이게 필수!! 이걸 선언하지 않으면 JPA가 언제 필드를 채울 지 몰라 null로 남는다.
public class SpringPracticeApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringPracticeApplication.class, args);
  }

}
