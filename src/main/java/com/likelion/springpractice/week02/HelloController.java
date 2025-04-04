package com.likelion.springpractice.week02;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //컨트롤러랑 ReponceBody가 합쳐진것임!! 클래스가 REST AOI 컨트롤러임을 선언!!
@RequestMapping("/api")
//API 엔트포인트를 설정 실제 들어갈 때, api/hello 이런식으로 들어가게됨!! 이 아래에있는 모든 컨트롤러의 설정에 들어가게됨!!
public class HelloController {

  @GetMapping("/hello") //http get 요청을 처리하는 api임을 선언해주는 것!!
  public String helloWorld() {
    return "Hello World";
  }
}
