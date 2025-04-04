package com.likelion.springpractice.week02;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // @Controller + @ResponseBody
@RequestMapping("/api") // API 엔드포인트 설정
public class HelloController {

  @GetMapping("/hello")
  public String helloWorld() {
    return "Hello World";
  }
}
