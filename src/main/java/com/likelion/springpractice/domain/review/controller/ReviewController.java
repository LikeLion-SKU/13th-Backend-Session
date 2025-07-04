package com.likelion.springpractice.domain.review.controller;

import com.likelion.springpractice.domain.review.dto.request.CreateReviewRequest;
import com.likelion.springpractice.domain.review.dto.request.UpdateReviewRequest;
import com.likelion.springpractice.domain.review.dto.response.CreateReviewResponse;
import com.likelion.springpractice.domain.review.dto.response.DeleteReviewResponse;
import com.likelion.springpractice.domain.review.dto.response.UpdateReviewResponse;
import com.likelion.springpractice.domain.review.service.ReviewService;
import com.likelion.springpractice.global.response.BaseResponse;
import com.likelion.springpractice.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@Tag(name = "Review", description = "리뷰 관련 API")
public class ReviewController {

  private final ReviewService reviewService;

  @PostMapping("/foods/{foodId}")
  @Operation(summary = "리뷰 작성", description = "음식에 대한 리뷰를 작성합니다.")
  public ResponseEntity<BaseResponse<CreateReviewResponse>> createReview(
      @PathVariable Long foodId,
      @RequestBody @Valid CreateReviewRequest request,
      @AuthenticationPrincipal CustomUserDetails userDetails) {

    CreateReviewResponse response = reviewService.createReview(userDetails.getUser().getId(), foodId, request);
    return ResponseEntity.ok(BaseResponse.success("리뷰 작성 완료", response));
  }

  @PatchMapping("/{reviewId}")
  @Operation(summary = "리뷰 수정", description = "작성한 리뷰를 수정합니다.")
  public ResponseEntity<BaseResponse<UpdateReviewResponse>> updateReview(
      @PathVariable Long reviewId,
      @RequestBody @Valid UpdateReviewRequest request,
      @AuthenticationPrincipal CustomUserDetails userDetails) {

    UpdateReviewResponse response = reviewService.updateReview(userDetails.getUser().getId(), reviewId, request);
    return ResponseEntity.ok(BaseResponse.success("리뷰가 수정되었습니다.", response));
  }

  @DeleteMapping("/{reviewId}")
  @Operation(summary = "리뷰 삭제", description = "작성한 리뷰를 삭제합니다.")
  public ResponseEntity<BaseResponse<DeleteReviewResponse>> deleteReview(
      @PathVariable Long reviewId,
      @AuthenticationPrincipal CustomUserDetails userDetails) {

    DeleteReviewResponse response = reviewService.deleteReview(userDetails.getUser().getId(), reviewId);
    return ResponseEntity.ok(BaseResponse.success("리뷰가 삭제되었습니다.", response));
  }
}