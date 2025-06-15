package com.likelion.springpractice.domain.post.week04.controller;

import com.fasterxml.jackson.databind.ser.Serializers.Base;
import com.likelion.springpractice.domain.post.week05.dto.reponse.PostResponse;
import com.likelion.springpractice.domain.post.week05.dto.request.CreatePostRequest;
import com.likelion.springpractice.domain.post.week05.dto.request.UpdatePostRequest;
import com.likelion.springpractice.domain.post.week05.service.PostService;
import com.likelion.springpractice.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
@RequiredArgsConstructor // 필수적인 필드만 포함하는 생성자를 자동으로 생성
@RequestMapping("/api/v1")
@Tag(name = "Post", description = "게시글 관련 API") // API 카테고리 설명
public class PostController {

  private final PostService postService;

  // @Operation : 이 API가 어떤 기능을 하는지 설명
  @Operation(summary = "게시글 생성",
      description = "게시판 페이지에서 게시글 작성 후 생성 버튼을 눌렀을 때 요청되는 API")
  @PostMapping("/posts")
  public ResponseEntity<BaseResponse<PostResponse>> createPost(
      @RequestBody @Valid CreatePostRequest createPostRequest) { // dto
    PostResponse response = postService.createPost(createPostRequest);
    return ResponseEntity.ok(BaseResponse.success("게시글 생성 성공", response));
  }

  @Operation(summary = "게시글 전체 조회",
      description = "게시판 페이지로 이동할 때 요청되는 API")
  @GetMapping("/posts")
  public ResponseEntity<BaseResponse<List<PostResponse>>> getAllPosts() {
    List<PostResponse> postList = postService.getAllPosts();
    return ResponseEntity.ok(BaseResponse.success("게시글 전체 조회 성공", postList));
  }

  // 5주차 기능 추가 (게시글 최신순으로 조회)
  @Operation(summary = "게시글을 최신순으로 조회",
      description = "게시판 페이지에서 최신순 버튼을 눌렀을 때 게시글을 최신순으로 조회하는 API")
  @GetMapping("/posts/latest")
  public ResponseEntity<BaseResponse<List<PostResponse>>> getLatestPosts() {
    List<PostResponse> postLastestList = postService.getAllPostsByCreatedAtDesc();
    return ResponseEntity.ok(BaseResponse.success("게시글 최신순으로 조회 성공", postLastestList));
  }

  // 5주차 기능 추가 (게시글 조회수 순으로 조회)
  @Operation(summary = "게시글을 조회수 순으로 조회",
      description = "게시판 페이지에서 조회수순 버튼을 눌렀을 때 게시글을 최신순으로 조회하는 API")
  @GetMapping("/posts/views")
  public ResponseEntity<List<PostResponse>> getViewsPosts() {
    return ResponseEntity.ok(postService.getAllPostsByViewsDesc());
  }

  @Operation(summary = "게시글 단일 조회",
      description = "게시판 페이지에서 특정 게시글에 접근할 때 요청되는 API")
  @GetMapping("/posts/{id}")
  public ResponseEntity<BaseResponse<PostResponse>> getPostById(@Parameter(description = "특정 게시글 ID") @PathVariable Long id) {
    PostResponse response = postService.getPostById(id);
    return ResponseEntity.ok(BaseResponse.success("게시글 조회 성공", response));
  }

  @Operation(summary = "게시글 수정",
      description = "게시판 페이지에서 게시글 수정 후 수정 완료 버튼을 눌러쓸 때 요청되는 API")
  @PutMapping("/posts/{id}")
  public ResponseEntity<BaseResponse<PostResponse>> updatePost(@RequestBody @Valid UpdatePostRequest updatePostRequest,
      @Parameter(description = "특정 게시글 ID") @PathVariable  Long id) { // dto
    PostResponse response = postService.updatePost(id, updatePostRequest);
    return ResponseEntity.ok(BaseResponse.success("게시글 수정 성공", response));
  }

  @Operation(summary = "게시글 삭제",
      description = "게시판 페이지에서 게시글 삭제 버튼을 눌렀을 때 요청되는 API")
  @DeleteMapping("/posts/{id}")
  public ResponseEntity<BaseResponse<Boolean>> deletePost(@Parameter(description = "특정 게시글 ID") @PathVariable Long id) {
    Boolean result = postService.deletePost(id);
    return ResponseEntity.ok(BaseResponse.success("게시글 삭제 성공", result));
  }

}
