package com.likelion.springpractice.domain.post.week04.controller;

import com.likelion.springpractice.domain.post.week04.entity.Post;
import com.likelion.springpractice.domain.post.week05.dto.request.CreatePostRequest;
import com.likelion.springpractice.domain.post.week05.dto.request.UpdatePostRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "Post", description = "게시글 관련 API")
public class PostController {

  @Operation(summary = "게시글 생성",
    description = "게시판 페이지에서 게시글 작성 후 생성 버튼을 눌렀을 때 요청되는 API")
  @PostMapping("/posts")
  public String createPost(@Parameter(description = "게시글 작성 내용") @RequestBody CreatePostRequest createPostRequest) {
    return "게시글 생성";
  }

  @Operation(summary = "게시글 전체 조회",
    description = "게시판 페이지로 이용할 때 요청되는 API")
  @GetMapping("/posts")
  public String getAllPosts() {
    return "게시글 전체 조회";
  }

  @Operation(summary = "게시글 단일 조회",
    description = "게시판 페이지에서 특정 게시글에 접근할 때 요청되는 API")
  @GetMapping("/posts/{id}")
  public String getPostById(@Parameter(description = "특정 게시글 ID") @PathVariable Long id) {
    return id + "빈 게시글 조회";
  }

  @Operation(summary = "게시글 수정",
    description = "게시판 페이지에서 게시글 수정 후 수정 완료 버튼을 눌렀을 때 요청되는 API")
  @PutMapping("/posts/{id}")
  public String updatePost(
      @Parameter(description = "게시글 수정 내용")@RequestBody UpdatePostRequest updatePostRequest,
      @Parameter(description = "특정 게시글 ID") @PathVariable Long id) {
    return "빈 게시글 수정";
  }

  @Operation(summary = "게시글 삭제",
    description = "게시판 페이지에서 게시글 삭제 버튼을 눌렀을 때 요청되는 API")
  @DeleteMapping("/posts/{id}")
  public String deletePost(@Parameter(description = "특정 게시글 ID") @PathVariable Long id) {
    return id + "빈 게시글 삭제";
  }
}
