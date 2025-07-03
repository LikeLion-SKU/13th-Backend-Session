package com.likelion.springpractice.domain.favoritefood.controller;

import com.likelion.springpractice.domain.favoritefood.dto.response.FavoriteFoodResponse;
import com.likelion.springpractice.domain.favoritefood.service.FavoriteFoodService;
import com.likelion.springpractice.global.response.BaseResponse;
import com.likelion.springpractice.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/foods/{foodId}/like")
@Tag(name = "Favorite", description = "음식 좋아요 API")
public class FavoriteFoodController {

  private final FavoriteFoodService favoriteService;

  @PostMapping
  @Operation(summary = "음식 좋아요 등록", description = "사용자가 음식을 좋아요합니다.")
  public ResponseEntity<BaseResponse<FavoriteFoodResponse>> likeFood(
      @PathVariable Long foodId,
      @AuthenticationPrincipal CustomUserDetails userDetails) {
    FavoriteFoodResponse response = favoriteService.likeFood(userDetails.getUser().getId(), foodId);
    return ResponseEntity.ok(BaseResponse.success("좋아요 완료", response));
  }

  @DeleteMapping
  @Operation(summary = "음식 좋아요 취소", description = "사용자가 음식 좋아요를 취소합니다.")
  public ResponseEntity<BaseResponse<FavoriteFoodResponse>> unlikeFood(
      @PathVariable Long foodId,
      @AuthenticationPrincipal CustomUserDetails userDetails) {
    FavoriteFoodResponse response = favoriteService.unlikeFood(userDetails.getUser().getId(), foodId);
    return ResponseEntity.ok(BaseResponse.success("좋아요 취소 완료", response));
  }
}