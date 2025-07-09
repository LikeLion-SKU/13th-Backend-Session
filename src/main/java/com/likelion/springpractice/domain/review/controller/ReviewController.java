package com.likelion.springpractice.domain.review.controller;

import com.likelion.springpractice.domain.review.dto.request.CreateReviewRequest;
import com.likelion.springpractice.domain.review.dto.request.GetReviewRequest;
import com.likelion.springpractice.domain.review.dto.response.ReviewResponse;
import com.likelion.springpractice.domain.review.entity.Review;
import com.likelion.springpractice.domain.review.service.ReviewService;
import com.likelion.springpractice.global.response.BaseResponse;
import com.likelion.springpractice.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/api/reviews")
@Tag(name = "Review", description = "리뷰 관련 API")
public class ReviewController {

  private final ReviewService reviewService;

  @Operation(summary = "리뷰 작성 API",
      description = "음식 상세 페이지에서 리뷰 생성 버튼을 눌렀을 때 요청되는 API")
  @PostMapping
  public ResponseEntity<BaseResponse<ReviewResponse>> createReview(
      @RequestBody @Valid CreateReviewRequest createReviewRequest,
      @AuthenticationPrincipal(expression = "username") String username) {
    ReviewResponse reviewResponse = reviewService.createReview(username, createReviewRequest);
    return ResponseEntity.ok(BaseResponse.success("리뷰 작성 완료", reviewResponse));
  }

  @Operation(summary = "특정 음식 리뷰 조회 API",
      description = "음식 상세 페이지에서 특정 음식의 리뷰 조회 버튼을 눌렀을 때 요청되는 API")
  @GetMapping("/{foodId}")
  public ResponseEntity<BaseResponse<List<ReviewResponse>>> getReview(
      @Parameter(description = "음식 ID") @PathVariable Long foodId) {
    List<ReviewResponse> reviewList = reviewService.getReviews(foodId);
    return ResponseEntity.ok(BaseResponse.success("리뷰 조회 성공", reviewList));
  }

  @Operation(summary = "특정 음식 리뷰 삭제 API",
      description = "음식 상세 페이지에서 특정 음식의 리뷰 삭제 버튼을 눌렀을 때 요청되는 API")
  @DeleteMapping("/{foodId}/review")
  public ResponseEntity<BaseResponse<Boolean>> deleteReview(@Parameter(description = "특정 음식 ID")
      @PathVariable Long foodId,
      @AuthenticationPrincipal CustomUserDetails userDetails) {
    Long userId = userDetails.getUser().getUserId();
    Boolean result = reviewService.deleteReview(userId, foodId);
    return ResponseEntity.ok(BaseResponse.success("리뷰 삭제 성공", result));
  }

}
