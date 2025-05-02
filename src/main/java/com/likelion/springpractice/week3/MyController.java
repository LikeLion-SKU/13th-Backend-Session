package com.likelion.springpractice.week3;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MyController {

  @PostMapping("/hello-create")
  public String helloCreate() {
    return "create hello";
  }

  @GetMapping("/hello-get")
  public String helloGet() {
    return "read hello";
  }

  @PutMapping("/hello-put")
  public String helloPut() {
    return "update put hello";
  }

  @PatchMapping("/hello-patch")
  public String helloPatch() {
    return "update patch hello";
  }

  @DeleteMapping("/hello-delete")
  public String helloDelete() {
    return "delete hello";
  }

}
