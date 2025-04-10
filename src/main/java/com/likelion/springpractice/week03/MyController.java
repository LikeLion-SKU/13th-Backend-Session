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
@RequestMapping("/api")
public class MyController {
  @PostMapping("/create-post")
  public String createPost(){
    return "새로운 게시글이 등록되었습니다.";
  }

  @GetMapping("read-post/{Id}")
  public String readPost(){
    return "게시글을 조회하였습니다.";
  }

  @PatchMapping("update-post/{Id}")
  public String updatePost(@PathVariable Long Id){
    return "게시글" + Id + "을 수정하였습니다.";
  }

  @PutMapping("update-post/{Id}")
  public String updatePutPost(@PathVariable Long Id){
    return "게시글" + Id + "을 전체 수정하였습니다.";
  }

  @DeleteMapping("delete-post/{Id}")
  public String deletePost(@PathVariable Long Id){
    return "게시글" + Id + "을 삭제하였습니다.";
  }
}

