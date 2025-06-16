package com.likelion.springpractice.domain.review.controller;

import com.likelion.springpractice.domain.review.dto.request.CreateReviewRequest;
import com.likelion.springpractice.domain.review.dto.response.ReviewResponse;
import com.likelion.springpractice.domain.review.service.ReviewService;
import com.likelion.springpractice.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
      @Parameter(description = "리뷰 내용") @RequestBody CreateReviewRequest createReviewRequest,
      @AuthenticationPrincipal(expression = "username") String username) {
    ReviewResponse reviewResponse = reviewService.createReview(username, createReviewRequest);
    return ResponseEntity.ok(BaseResponse.success("리뷰 작성 완료", reviewResponse));
  }


}
