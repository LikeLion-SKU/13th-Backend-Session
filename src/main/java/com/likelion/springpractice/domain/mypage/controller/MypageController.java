package com.likelion.springpractice.domain.mypage.controller;

import com.likelion.springpractice.domain.mypage.dto.response.MypageBadgesResponse;
import com.likelion.springpractice.domain.mypage.dto.response.MypageLikesResponse;
import com.likelion.springpractice.domain.mypage.dto.response.MypageReviewsResponse;
import com.likelion.springpractice.domain.mypage.service.MypageService;
import com.likelion.springpractice.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mypages")
@Tag(name = "Mypage", description = "마이 페이지 관련 API")
public class MypageController {

  private final MypageService mypageService;

  @Operation(summary = "좋아요 리스트 조회 API", description = "마이 페이지에서 좋아요 리스트 조회 버튼을 누르면 요청되는 API")
  @GetMapping("/likes")
  public ResponseEntity<BaseResponse<List<MypageLikesResponse>>> getAllLikeFoods(
      @AuthenticationPrincipal(expression = "username") String username
  ) {
    List<MypageLikesResponse> mypageLikesResponses = mypageService.getLikeFoods(username);
    return ResponseEntity.ok(BaseResponse.success("좋아요 리스트 조회 완료", mypageLikesResponses));
  }

  @Operation(summary = "리뷰 리스트 조회 API", description = "마이 페이지에서 리뷰 리스트 조회 버튼을 누르면 요청되는 API")
  @GetMapping("/reviews")
  public ResponseEntity<BaseResponse<List<MypageReviewsResponse>>> getReviewFoods(
      @AuthenticationPrincipal(expression = "username") String username
  ) {
    List<MypageReviewsResponse> mypageReviewsResponses = mypageService.getReviewFoods(username);
    return ResponseEntity.ok(BaseResponse.success("리뷰 리스트 조회 성공", mypageReviewsResponses));
  }

  @Operation(summary = "배찌 리스트 조회 API", description = "마이 페이지에서 배찌 조회 버튼을 누르면 요청되는 API")
  @GetMapping("/badges")
  public ResponseEntity<BaseResponse<List<MypageBadgesResponse>>> getUserBadges(
      @AuthenticationPrincipal(expression = "username") String username
  ) {
    List<MypageBadgesResponse> mypageBadgesResponses = mypageService.getUserBadges(username);
    return ResponseEntity.ok(BaseResponse.success("사용자 배찌 조회 성공", mypageBadgesResponses));
  }

}
