package com.likelion.springpractice.domain.review.controller;

import com.likelion.springpractice.domain.review.dto.request.ReviewCreateRequest;
import com.likelion.springpractice.domain.review.dto.request.ReviewUpdateRequest;
import com.likelion.springpractice.domain.review.dto.response.ReviewListResponse;
import com.likelion.springpractice.domain.review.dto.response.ReviewResponse;
import com.likelion.springpractice.domain.review.service.ReviewService;
import com.likelion.springpractice.global.response.BaseResponse;
import com.likelion.springpractice.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Review", description = "음식 리뷰 API")
public class ReviewController {

  private final ReviewService reviewService;

  @Operation(summary = "리뷰 작성", description = "특정 음식에 대해 리뷰를 작성합니다.")
  @PostMapping("/foods/{foodId}/reviews")
  public ResponseEntity<BaseResponse<ReviewResponse>> createReview(
      @Parameter(description = "리뷰를 작성할 음식 ID") @PathVariable Long foodId,
      @Valid @RequestBody ReviewCreateRequest dto,
      @AuthenticationPrincipal CustomUserDetails user) {

    ReviewResponse res = reviewService.createReview(foodId, dto, user.getUserId());
    return ResponseEntity.ok(BaseResponse.success("리뷰 작성 성공", res));
  }

  @Operation(summary = "음식별 리뷰 목록", description = "특정 음식에 대한 모든 리뷰를 조회합니다.")
  @GetMapping("/foods/{foodId}/reviews")
  public ResponseEntity<BaseResponse<ReviewListResponse>> getReviews(
      @Parameter(description = "조회할 음식 ID") @PathVariable Long foodId) {

    return ResponseEntity.ok(
        BaseResponse.success("리뷰 목록 조회 성공",
            reviewService.getReviewsByFoodId(foodId)));
  }

  @Operation(summary = "리뷰 수정", description = "자신이 작성한 리뷰를 수정합니다.")
  @PatchMapping("/reviews/{reviewId}")
  public ResponseEntity<BaseResponse<ReviewResponse>> updateReview(
      @Parameter(description = "수정할 리뷰 ID") @PathVariable Long reviewId,
      @Valid @RequestBody ReviewUpdateRequest dto,
      @AuthenticationPrincipal CustomUserDetails user) {

    ReviewResponse res = reviewService.updateReview(reviewId, dto, user.getUserId());
    return ResponseEntity.ok(BaseResponse.success("리뷰 수정 성공", res));
  }

  @Operation(summary = "리뷰 삭제", description = "자신이 작성한 리뷰를 삭제합니다.")
  @DeleteMapping("/reviews/{reviewId}")
  public ResponseEntity<BaseResponse<Boolean>> deleteReview(
      @Parameter(description = "삭제할 리뷰 ID") @PathVariable Long reviewId,
      @AuthenticationPrincipal CustomUserDetails user) {

    return ResponseEntity.ok(
        BaseResponse.success("리뷰 삭제 성공",
            reviewService.deleteReview(reviewId, user.getUserId())));
  }
}

