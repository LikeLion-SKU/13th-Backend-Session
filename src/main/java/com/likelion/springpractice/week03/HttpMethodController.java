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
  @PostMapping("/create-test")
  public String createTest(){
    return "This is create test";
  }
  @GetMapping("read-test")
  public String readTest(){
    return "This is read test";
  }
  @PatchMapping("update-test")
  public String updatePatchTest(){
    return "This is update patch test";
  }
  @PutMapping("update-test")
  public String updatePutTest(){
    return "This is update put test";
  }
  @DeleteMapping("delete-test")
  public String deleteTest(){
    return "This is delete test";
  }
}
