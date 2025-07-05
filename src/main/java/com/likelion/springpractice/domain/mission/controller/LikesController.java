package com.likelion.springpractice.domain.mission.controller;

import com.likelion.springpractice.domain.mission.dto.response.FoodSimpleResponse;
import com.likelion.springpractice.domain.mission.service.LikeService;
import com.likelion.springpractice.global.response.BaseResponse;
import com.likelion.springpractice.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/likes")
@RequiredArgsConstructor
@Tag(name = "Likes", description = "좋아요 관련 API")
public class LikesController {

  private final LikeService likesService;

  //좋아요 추가,취소 API
  @Operation(summary = "좋아요 등록/취소 API", description = "좋아요 하기, 한번 더 하면 취소")
  @PostMapping("/{foodId}")
  public ResponseEntity<BaseResponse<String>> toggle(
      @AuthenticationPrincipal CustomUserDetails userDetails,
      @PathVariable Long foodId
  ) {
    likesService.toggleLike(userDetails.getUser(), foodId);
    return ResponseEntity.ok(BaseResponse.success("좋아요 토글 완료", null));
  }

  //좋아요한 음식 리스트 조회
  @Operation(summary = "좋아요 한 음식 리스트 조회 API", description = "좋아요 한 음식 리스트 조회를 위한 API")
  @GetMapping
  public ResponseEntity<BaseResponse<List<FoodSimpleResponse>>> likedFoods(
      @AuthenticationPrincipal CustomUserDetails userDetails
  ) {
    List<FoodSimpleResponse> result = likesService.getLikedFoods(userDetails.getUser()).stream()
        .map(food -> new FoodSimpleResponse(food.getId(), food.getName(), food.getLikeCount()))
        .collect(Collectors.toList());
    return ResponseEntity.ok(BaseResponse.success("좋아요한 음식 목록", result));
  }
}