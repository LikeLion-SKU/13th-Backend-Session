package com.likelion.springpractice.domain.user.controller;

import com.likelion.springpractice.domain.Like.dto.response.LikeFoodResponse;
import com.likelion.springpractice.domain.Review.dto.response.ReviewResponse;
import com.likelion.springpractice.domain.badge.dto.response.BadgeResponse;
import com.likelion.springpractice.domain.user.dto.response.MyPageResponse;
import com.likelion.springpractice.domain.user.dto.response.MyPageUserResponse;
import com.likelion.springpractice.domain.user.entity.User;
import com.likelion.springpractice.domain.user.service.MyPageService;
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
@RequestMapping("/api/users/me")
@RequiredArgsConstructor
@Tag(name = "MyPage", description = "마이페이지 API")
public class MyPageController {

  private final MyPageService myPageService;

  @GetMapping
  @Operation(summary = "내 기본 정보 조회")
  public ResponseEntity<BaseResponse<MyPageUserResponse>> getMyInfo(
      @AuthenticationPrincipal CustomUserDetails userDetails) {

    User user = userDetails.getUser();
    MyPageUserResponse response = myPageService.getMyInfo(user);
    return ResponseEntity.ok(BaseResponse.success("내 정보 조회 성공", response));
  }

  @GetMapping("/reviews")
  @Operation(summary = "내가 작성한 후기 목록 조회")
  public ResponseEntity<BaseResponse<List<ReviewResponse>>> getMyReviews(
      @AuthenticationPrincipal CustomUserDetails userDetails) {

    User user = userDetails.getUser();
    List<ReviewResponse> response = myPageService.getMyReviews(user);
    return ResponseEntity.ok(BaseResponse.success("내 후기 조회 성공", response));
  }

  @GetMapping("/likes")
  @Operation(summary = "내가 좋아요한 음식 목록 조회")
  public ResponseEntity<BaseResponse<List<LikeFoodResponse>>> getMyLikes(
      @AuthenticationPrincipal CustomUserDetails userDetails) {

    User user = userDetails.getUser();
    List<LikeFoodResponse> response = myPageService.getMyLikes(user);
    return ResponseEntity.ok(BaseResponse.success("좋아요한 음식 조회 성공", response));
  }

  @GetMapping("/badges")
  @Operation(summary = "내가 획득한 뱃지 목록 조회")
  public ResponseEntity<BaseResponse<List<BadgeResponse>>> getMyBadges(
      @AuthenticationPrincipal CustomUserDetails userDetails) {

    User user = userDetails.getUser();
    List<BadgeResponse> response = myPageService.getMyBadges(user);
    return ResponseEntity.ok(BaseResponse.success("뱃지 조회 성공", response));
  }

  @GetMapping("/full")
  @Operation(summary = "마이페이지 전체 정보 통합 조회")
  public ResponseEntity<BaseResponse<MyPageResponse>> getMyPage(
      @AuthenticationPrincipal CustomUserDetails userDetails) {

    User user = userDetails.getUser();
    MyPageResponse response = myPageService.getMyPage(user);
    return ResponseEntity.ok(BaseResponse.success("마이페이지 전체 조회 성공", response));
  }

}
