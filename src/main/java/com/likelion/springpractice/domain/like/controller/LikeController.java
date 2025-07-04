package com.likelion.springpractice.domain.like.controller;

import com.likelion.springpractice.domain.like.dto.response.LikeResponse;
import com.likelion.springpractice.domain.like.service.LikeService;
import com.likelion.springpractice.global.response.BaseResponse;
import com.likelion.springpractice.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/likes/foods")
@Tag(name = "likes", description = "좋아요 관련 API")
public class LikeController {

  private final LikeService likeService;

  @Operation(
      summary = "좋아요 등록",
      description = "특정 음식에 대한 좋아요 등록 요청 API")
  @PostMapping("/{foodId}")
  public ResponseEntity<BaseResponse<LikeResponse>> likeFood(
      @Parameter(description = "좋아요를 등록할 음식의 ID")
      @PathVariable Long foodId,
      @AuthenticationPrincipal CustomUserDetails userDetails) {
    LikeResponse response = likeService.likeFood(foodId, userDetails.getUser());
    return ResponseEntity.ok(BaseResponse.success("좋아요 등록 성공", response));
  }

  @Operation(
      summary = "좋아요 취소",
      description = "특정 음식에 대한 좋아요 취소 요청 API")
  @DeleteMapping("/{foodId}")
  public ResponseEntity<BaseResponse<LikeResponse>> unlikeFood(
      @Parameter(description = "좋아요를 취소할 음식의 ID")
      @PathVariable Long foodId,
      @AuthenticationPrincipal CustomUserDetails userDetails) {
    LikeResponse response = likeService.unlikeFood(foodId, userDetails.getUser());
    return ResponseEntity.ok(BaseResponse.success("좋아요 취소 성공", response));
  }

  @Operation(
      summary = "음식에 대한 좋아요 조회",
      description = "특정 음식에 대한 좋아요 여부 및 총 좋아요 개수를 조회하는 API")
  @GetMapping("/{foodId}")
  public ResponseEntity<BaseResponse<LikeResponse>> getLikeByUser(
      @Parameter(description = "좋아요 여부를 조회할 음식의 ID")
      @PathVariable Long foodId,
      @AuthenticationPrincipal CustomUserDetails userDetails) {
    LikeResponse response = likeService.getLikeByUser(foodId, userDetails.getUser());
    return ResponseEntity.ok(BaseResponse.success("음식에 대한 좋아요 조회 성공", response));
  }


}
