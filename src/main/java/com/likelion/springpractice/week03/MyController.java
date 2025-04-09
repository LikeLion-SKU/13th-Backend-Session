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
@RequestMapping("/api/todo")
public class MyController {

  //Post - 할 일 생성
  @PostMapping
  public String createTodo() {
    return "할 일이 생성되었습니다.";
  }

  //Get - 할 일 조회
  @GetMapping("read-todo/{id}")
  public String readTodo(@PathVariable Long id) {
    return "할 일 조회 : ID = " +id;
  }

  //Patch - 할 일 상태 변경 (즉 일부 변경)
  @PatchMapping("update-todo/{id}")
  public String updateTodoStatus(@PathVariable Long id) {
    return "할 일 상태 일부 수정됨 : ID = " +id;
  }

  //Put - 할 일 전체 내용 수정
  @PutMapping("update-todo/{id}")
  public String updateTodoAll(@PathVariable Long id) {
    return "할 일 전체 수정됨 : ID = " +id;
  }

  //Delete - 할 일 삭제
  @DeleteMapping("delete-todo/{id}")
  public String deleteTodo(@PathVariable Long id) {
    return "할 일이 삭제됨 : ID = " +id;
  }

}
