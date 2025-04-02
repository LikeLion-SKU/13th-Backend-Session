package com.likelion.springpractice;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@RequestMapping("/api")
public class HelloController {

  @GetMapping("/hello")
  public String helloWorld() {
    return "Hello World!";
  }
}
