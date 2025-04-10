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

  @PostMapping("/data-registration")
  public String dataPost() {
    return "회원가입,로그인,게시글 생성,댓글작성";
  }

  @GetMapping("data-check")
  public String dataGet() {
    return "사용자 조회, 게시글 조회,댓글 조회";
  }

  @PatchMapping("update-patch-content")
  public String updatePatchContent() {
    return "비밀번호 변경,닉네임 변경, 게시글 수정,댓글 수정";
  }

  @PutMapping("update-put-content")
  public String updatePutContent() {
    return "비밀번호 변경, 닉네임 변경, 게시글 수정,댓글 수정";
  }

  @DeleteMapping("data-delete")
  public String dataDelete() {
    return "회원탈퇴, 게시글 삭제, 댓글 삭제";
  }


}
