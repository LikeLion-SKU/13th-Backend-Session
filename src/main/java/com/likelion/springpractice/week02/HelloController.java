package com.likelion.springpractice.week02;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloController {
  @GetMapping("/hello")
  public String helloWorld() {
    return "Hello World!";
  }
}
