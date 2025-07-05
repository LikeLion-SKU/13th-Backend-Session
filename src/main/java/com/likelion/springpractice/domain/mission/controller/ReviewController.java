package com.likelion.springpractice.domain.mission.controller;

import com.likelion.springpractice.domain.mission.dto.request.ReviewRequest;
import com.likelion.springpractice.domain.mission.dto.response.ReviewResponse;
import com.likelion.springpractice.domain.mission.service.ReviewService;
import com.likelion.springpractice.global.response.BaseResponse;
import com.likelion.springpractice.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@Tag(name = "review", description = "리뷰 관련 API")
public class ReviewController {

  private final ReviewService reviewService;

  @PostMapping
  @Operation(summary = "리뷰 작성 API", description = "리뷰 작성을 위한 API")
  public ResponseEntity<BaseResponse<ReviewResponse>> create(
      @AuthenticationPrincipal CustomUserDetails userDetails,
      @RequestBody @Valid ReviewRequest request
  ) {
    return ResponseEntity.ok(BaseResponse.success("리뷰 작성 완료",
        reviewService.createReview(userDetails.getUser(), request)));
  }

  @Operation(summary = "리뷰 조회 API", description = "리뷰 아이디 입력하면 됩니다.")
  @GetMapping("/food/{foodId}")
  public ResponseEntity<BaseResponse<List<ReviewResponse>>> foodReviews(@PathVariable Long foodId) {
    return ResponseEntity.ok(BaseResponse.success("리뷰 조회 완료",
        reviewService.getReviewsByFoodId(foodId)));
  }

  @Operation(summary = "내 리뷰목록 조회 API", description = "내 리뷰 목록 조회를 위한 API")
  @GetMapping("/my-reviews")
  public ResponseEntity<BaseResponse<List<ReviewResponse>>> myReviews(
      @AuthenticationPrincipal CustomUserDetails userDetails
  ) {
    return ResponseEntity.ok(BaseResponse.success("내 리뷰 목록 조회 완료",
        reviewService.getMyReviews(userDetails.getUser())));
  }
}
