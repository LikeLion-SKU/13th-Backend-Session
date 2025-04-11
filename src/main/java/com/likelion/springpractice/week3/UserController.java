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
public class UserController {


  // 회원 관리 //
  @PostMapping("/users")
  public String addUser(){
    return "User-add_success";
  }

  @GetMapping("user")
  public String getUser(){
    return "User-get_success";
  }

  @PutMapping("user")
  public String changeUser(){
    return "User-change_success";
  }

  @PatchMapping("user")
  public String updateUser(){
    return "User-update_success";
  }

  @DeleteMapping("user")
  public String deleteUser(){
    return "User-delete_success";
  }
}
