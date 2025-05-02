package com.likelion.springpractice.domain.post.week04.controller;

import com.likelion.springpractice.domain.post.week05.dto.request.CreatePostRequest;
import com.likelion.springpractice.domain.post.week05.dto.request.UpdatePostRequest;
import com.likelion.springpractice.domain.post.week05.dto.response.PostResponse;
import com.likelion.springpractice.domain.post.week05.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

  private final PostService postService;

  @Operation(summary = "게시글 생성",
      description = "게시판 페이지에서 게시글 작성 후 생성 버튼을 눌렀을 때 요청되는 API")
  @PostMapping("/posts")
  public ResponseEntity<PostResponse> createPost(
      @Parameter(description = "게시글 작성 내용") @RequestBody CreatePostRequest createPostRequest){
    return ResponseEntity.ok(postService.createPost(createPostRequest));
  }

  @Operation(summary = "게시글 전체 조회",
      description = "게시판 페이지로 이동될 때 요청되는 API")
  @GetMapping("/posts")
  public ResponseEntity<List<PostResponse>> getAllPosts(){
    return ResponseEntity.ok(postService.getAllPosts());
  }

  @Operation(summary = "게시글 단일 조회",
      description = "게시판 페이지에서 특정 게시글에 접근할 때 요청되는 API")
  @GetMapping("/posts/{id}")
  public ResponseEntity<PostResponse> getPostById(
      @Parameter(description = "특정 게시글 ID")@PathVariable Long id){
    return ResponseEntity.ok(postService.getPostById(id));
  }

  @Operation(summary = "게시글 수정",
      description = "게시판 페이지에서 게시글 수정 후 수정 완료 버튼을 눌렀을 때 요청되는 API")
  @PutMapping("/posts/{id}")
  public ResponseEntity<PostResponse> updatePost(
      @Parameter(description = "게시글 수정 내용") @RequestBody UpdatePostRequest updatePostRequest,
      @Parameter(description = "특정 게시글 ID") @PathVariable Long id){
    return ResponseEntity.ok(postService.updatePost(id, updatePostRequest));
  }

  @Operation(summary = "게시글 삭제",
      description = "게시판 페이지에서 게시글 삭제 버튼을 눌렀을 때 요청되는 API")
  @DeleteMapping("/posts/{id}")
  public ResponseEntity<Boolean> deletePost(
      @Parameter(description = "특정 게시글 ID") @PathVariable Long id){
    return ResponseEntity.ok(postService.deletePost(id));
  }
}