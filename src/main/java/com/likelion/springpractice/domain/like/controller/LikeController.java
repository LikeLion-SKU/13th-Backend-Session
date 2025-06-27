package com.likelion.springpractice.domain.like.controller;

import com.likelion.springpractice.domain.like.dto.request.LikeRequest;
import com.likelion.springpractice.domain.like.dto.response.LikeResponse;
import com.likelion.springpractice.domain.like.service.LikeService;
import com.likelion.springpractice.global.response.BaseResponse;
import com.likelion.springpractice.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Like", description = "음식 좋아요 API")
public class LikeController {

  private final LikeService likeService;

  @Operation(summary = "좋아요/취소 토글", description = "사용자가 음식에 좋아요를 누르거나 취소합니다.")
  @PostMapping("/foods/{foodId}/likes")
  public ResponseEntity<BaseResponse<LikeResponse>> toggleLike(
      @Parameter(description = "좋아요 대상 음식 ID") @PathVariable Long foodId,
      @Valid @RequestBody LikeRequest request,
      @AuthenticationPrincipal CustomUserDetails user) {

    LikeResponse res = likeService.toggleLike(foodId, user.getUserId(), request);
    return ResponseEntity.ok(BaseResponse.success("좋아요 처리 완료", res));
  }
}
