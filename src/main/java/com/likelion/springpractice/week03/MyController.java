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
public class MyController {

  @GetMapping("/read")
  public String read() {
    return "This is read";
  }

  @PostMapping("create")
  public String create() {
    return "This is create";
  }

  @PutMapping("update")
  public String put() {
    return "This is put";
  }

  @PatchMapping("update")
  public String update() {
    return "This is patch";
  }

  @DeleteMapping("delete")
  public String delete() {
    return "This is delete";
  }
}
