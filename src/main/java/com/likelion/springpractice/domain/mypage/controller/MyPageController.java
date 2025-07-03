package com.likelion.springpractice.domain.mypage.controller;

import com.likelion.springpractice.domain.badge.dto.response.BadgeResponse;
import com.likelion.springpractice.domain.mypage.dto.response.FavoriteFoodResponse;
import com.likelion.springpractice.domain.mypage.dto.response.MyPageResponse;
import com.likelion.springpractice.domain.mypage.dto.response.ReviewResponse;
import com.likelion.springpractice.domain.mypage.service.MyPageService;
import com.likelion.springpractice.global.response.BaseResponse;
import com.likelion.springpractice.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mypage")
@Tag(name = "MyPage", description = "마이페이지 조회 API")
public class MyPageController {

  private final MyPageService myPageService;

  @Operation(summary = "마이페이지 조회", description = "현재 로그인한 유저의 기본 정보를 조회합니다.")
  @GetMapping
  public ResponseEntity<BaseResponse<MyPageResponse>> getMyPage(
      @AuthenticationPrincipal CustomUserDetails userDetails) {

    MyPageResponse response = myPageService.getMyPage(userDetails.getUser().getId());
    return ResponseEntity.ok(BaseResponse.success("마이페이지 조회 성공", response));
  }

  @Operation(summary = "좋아요한 음식 조회", description = "로그인한 사용자가 좋아요한 음식 리스트를 조회합니다.")
  @GetMapping("/favorites")
  public ResponseEntity<BaseResponse<List<FavoriteFoodResponse>>> getFavoriteFoods(
      @AuthenticationPrincipal CustomUserDetails userDetails) {

    List<FavoriteFoodResponse> favorites = myPageService.getFavorites(userDetails.getUser().getId());
    return ResponseEntity.ok(BaseResponse.success("좋아요한 음식 조회 성공", favorites));
  }

  @Operation(summary = "작성한 리뷰 조회", description = "로그인한 사용자가 작성한 리뷰 리스트를 조회합니다.")
  @GetMapping("/reviews")
  public ResponseEntity<BaseResponse<List<ReviewResponse>>> getReviews(
      @AuthenticationPrincipal CustomUserDetails userDetails) {

    List<ReviewResponse> reviews = myPageService.getReviews(userDetails.getUser().getId());
    return ResponseEntity.ok(BaseResponse.success("작성한 리뷰 조회 성공", reviews));
  }

  @Operation(summary = "획득한 뱃지 목록 조회", description = "로그인한 사용자가 획득한 뱃지를 조회합니다.")
  @GetMapping("/badges")
  public ResponseEntity<BaseResponse<List<BadgeResponse>>> getUserBadges(
      @AuthenticationPrincipal CustomUserDetails userDetails) {

    List<BadgeResponse> badges = myPageService.getUserBadges(userDetails.getUser().getId());
    return ResponseEntity.ok(BaseResponse.success("획득한 뱃지 조회 성공", badges));
  }

}
