package com.likelion.springpractice.week03;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
  @PostMapping
  public String CreateUser(){
    return "사용자 생성 완료";
  }

  @GetMapping("/{id}")
  public String readUser(@PathVariable Long id){
    return "id: "+id+"사용자 조회";
  }

  @GetMapping
  public String readUsers(){
    return "사용자 전체 조회";
  }

  @PatchMapping("/{id}")
  public String updatePatchUser(@PathVariable Long id){
    return "id: " + id + " 사용자 수정(정보 추가)";
  }

  @PutMapping("/{id}")
  public String updatePutUser(@PathVariable Long id){
    return "id: " + id + " 사용자 수정(정보 덮어쓰기)";
  }

  @DeleteMapping("/{id}")
  public String deleteUser(@PathVariable Long id){
    return "id: "+id+"사용자 삭제";
  }

  @DeleteMapping
  public String deleteUsers(){
    return "사용자 전체 삭제";
  }

}
