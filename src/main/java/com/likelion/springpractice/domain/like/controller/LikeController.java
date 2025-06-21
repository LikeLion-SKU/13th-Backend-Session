package com.likelion.springpractice.domain.like.controller;

import com.likelion.springpractice.domain.like.dto.request.LikeRequest;
import com.likelion.springpractice.domain.like.dto.response.LikeResponse;
import com.likelion.springpractice.domain.like.repository.LikeRepository;
import com.likelion.springpractice.domain.like.service.LikeService;
import com.likelion.springpractice.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/likes")
@Tag(name = "Like", description = "좋아요 관련 API")
public class LikeController {

  private final LikeService likeService;

  @Operation(summary = "좋아요 토글 API", description = "음식에 좋아요를 눌렀을 때 요청되는 API")
  @PostMapping
  public ResponseEntity<BaseResponse<Boolean>> likeToggle(
      @RequestBody LikeRequest likeRequest,
      @AuthenticationPrincipal(expression = "username") String username) {
    Boolean likeStatus = likeService.likeToggle(username, likeRequest.getFoodId());
    return ResponseEntity.ok(BaseResponse.success("좋아요 버튼 클릭", likeStatus));
  }

}
