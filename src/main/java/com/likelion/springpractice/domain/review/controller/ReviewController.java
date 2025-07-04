package com.likelion.springpractice.domain.review.controller;

import com.likelion.springpractice.domain.review.dto.request.ReviewCreateRequest;
import com.likelion.springpractice.domain.review.dto.response.ReviewDetailResponse;
import com.likelion.springpractice.domain.review.service.ReviewService;
import com.likelion.springpractice.global.response.BaseResponse;
import com.likelion.springpractice.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "Review", description = "Review 관리 API")
@Slf4j
public class ReviewController {

  private final ReviewService reviewService;


  @Operation(summary = "리뷰 생성 API", description = "새로운 리뷰를 생성하는 API")
  @PostMapping("/food/{foodId}/reviews")
  public ResponseEntity<BaseResponse<ReviewDetailResponse>> createReview(
      @AuthenticationPrincipal CustomUserDetails userDetails,
      @PathVariable Long foodId,
      @RequestBody @Valid ReviewCreateRequest reviewCreateRequest) {
    ReviewDetailResponse response = reviewService.createReview(foodId, userDetails.getUser(),
        reviewCreateRequest);
    return ResponseEntity.ok(BaseResponse.success("리뷰 생성 성공", response));

  }

  @Operation(summary = "리뷰 조회 API", description = "특정 음식에 대한 리뷰를 조회하는 API")
  @GetMapping("/food/{foodId}/reviews")
  public ResponseEntity<BaseResponse<List<ReviewDetailResponse>>> getReview(
      @PathVariable Long foodId) {
    List<ReviewDetailResponse> responses = reviewService.getReviewsByFoodId(foodId);
    return ResponseEntity.ok(BaseResponse.success("리뷰 조회 성공", responses));
  }

  @Operation(summary = "리뷰 삭제 API", description = "특정 리뷰를 삭제하는 API")
  @DeleteMapping("/reviews/{reviewId}")
  public ResponseEntity<BaseResponse<Void>> deleteReview(
      @AuthenticationPrincipal CustomUserDetails userDetails,
      @PathVariable Long reviewId) {
    reviewService.deleteReview(reviewId, userDetails.getUser());
    return ResponseEntity.ok(BaseResponse.success("리뷰 삭제 성공", null));
  }


}
