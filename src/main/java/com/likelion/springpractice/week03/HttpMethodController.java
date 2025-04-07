package com.likelion.springpractice.week03;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HttpMethodController {

  //생성하는 메서드!!
  @PostMapping("/create-test")
  public String createTest() {
    return "This is create test";
  }

  //조회하는 메서드!!
  @GetMapping("read-test")
  public String readTest() {
    return "This is read test";
  }

  //수정 메서드 1 Patch 일부 데이터를 수정할 때는 patch를 쓴다.
  @PatchMapping("update-test")
  public String updatePatchTest() {
    return "This is update test";
  }

  //수정 메서드 2 Put 아예 완전히 덮어쓰기 할 때 put을 쓴다.
  @PutMapping("update-test")
  public String updatePutTest() {
    return "This is update test";
  }

  //삭제 메서드!!
  @DeleteMapping("delete-test")
  public String deleteTest() {
    return "This is delete test";
  }
}
