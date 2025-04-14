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
public class PostController {
  //생성
  @PostMapping("/create-post")
  public String createPost() {
    return "This is create post";
  }
  //조회
  @GetMapping("get-post")
  public String readPost() {
    return "This is the post";
  }
  //일부 수정
  @PatchMapping("update-post")
  public String updatePatchPost() {
    return "This is update patch post";
  }
  //전체 수정
  @PutMapping("update-post")
  public String updatePutPost() {
    return "This is update put post";
  }
  //삭제
  @DeleteMapping("delete-post")
  public String deletePost() {
    return "This is delete post";
  }
}