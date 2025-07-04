package com.likelion.springpractice.domain.mypage.controller;

import com.likelion.springpractice.domain.badge.dto.response.BadgeResponse;
import com.likelion.springpractice.domain.like.dto.response.LikedFoodResponse;
import com.likelion.springpractice.domain.mypage.service.MyPageService;
import com.likelion.springpractice.domain.user.dto.response.UserResponse;
import com.likelion.springpractice.global.response.BaseResponse;
import com.likelion.springpractice.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/myPages")
@Tag(name = "MyPage", description = "MyPage 관련 API")
public class MyPageController {

  private final MyPageService myPageService;

  @Operation(
      summary = "사용자 정보 조회",
      description = "로그인한 사용자의 정보를 조회하는 API")
  @GetMapping("/profile")
  public ResponseEntity<BaseResponse<UserResponse>> getUserProfile(
      @AuthenticationPrincipal CustomUserDetails userDetails) {
    UserResponse response = myPageService.getUserInfo(userDetails.getUser().getUserId());
    return ResponseEntity.ok(BaseResponse.success("사용자 정보 조회 성공", response));
  }

  @Operation(
      summary = "로그인한 사용자가 좋아요한 음식 목록",
      description = "로그인한 사용자가 좋아요한 음식들의 목록을 반환하는 API")
  @GetMapping("/likes")
  public ResponseEntity<BaseResponse<List<LikedFoodResponse>>> getMyLikedFoods(
      @AuthenticationPrincipal CustomUserDetails userDetails) {
    List<LikedFoodResponse> likedFoods = myPageService.getLikedFoodsByUser(userDetails.getUser());
    return ResponseEntity.ok(BaseResponse.success("좋아요한 음식 목록 조회 성공", likedFoods));
  }

  @Operation(
      summary = "로그인한 사용자의 리뷰 목록",
      description = "로그인한 사용자가 작성한 리뷰들의 목록을 반환하는 API")
  @GetMapping("/reviews")
  public ResponseEntity<BaseResponse<List<LikedFoodResponse>>> getMyReviews(
      @AuthenticationPrincipal CustomUserDetails userDetails) {
    List<LikedFoodResponse> reviews = myPageService.getReviewsByUser(userDetails.getUser());
    return ResponseEntity.ok(BaseResponse.success("리뷰 목록 조회 성공", reviews));
  }


  @Operation(
      summary = "뱃지 조회",
      description = "사용자가 보유하고 있는 뱃지를 조회하는 API")
  @PostMapping("/badges")
  public ResponseEntity<BaseResponse<List<BadgeResponse>>> getUserBadges(
      @AuthenticationPrincipal
      CustomUserDetails userDetails) {
    List<BadgeResponse> responses = myPageService.getBadgeByUser(userDetails.getUser());
    return ResponseEntity.ok(BaseResponse.success("뱃지 조회 성공", responses));
  }
}

