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
    return "This is a create test";
  }

  @GetMapping("read-test")
  public String readTest(){
    return "This is a read test";
  }

  @PatchMapping("update-test")
  public String updateTest(){
    return "This is a update test";
  }

  @PutMapping("update-test")
  public String updatePutTest(){
    return "This is a update put test";
  }

  @DeleteMapping("delete-test")
  public String deleteTest(){
    return "This is a delete test";
  }
}

