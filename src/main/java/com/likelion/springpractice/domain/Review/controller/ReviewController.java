package com.likelion.springpractice.domain.Review.controller;

import com.likelion.springpractice.domain.Review.dto.request.ReviewRequest;
import com.likelion.springpractice.domain.Review.dto.response.ReviewResponse;
import com.likelion.springpractice.domain.Review.service.ReviewService;
import com.likelion.springpractice.domain.user.entity.User;
import com.likelion.springpractice.global.response.BaseResponse;
import com.likelion.springpractice.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@Tag(name = "Review", description = "후기 API")
public class ReviewController {

  private final ReviewService reviewService;

  @PostMapping
  @Operation(summary = "후기 작성")
  public ResponseEntity<BaseResponse<ReviewResponse>> writeReview(
      @AuthenticationPrincipal CustomUserDetails userDetails,
      @Valid @RequestBody ReviewRequest request) {

    User user = userDetails.getUser();
    ReviewResponse response = reviewService.writeReview(user, request);
    return ResponseEntity.ok(BaseResponse.success("후기 작성 성공", response));
  }

  @GetMapping("/food/{foodId}")
  @Operation(summary = "음식별 후기 조회")
  public ResponseEntity<BaseResponse<List<ReviewResponse>>> getReviewsByFood(
      @PathVariable Long foodId) {

    List<ReviewResponse> responses = reviewService.getReviewsByFood(foodId);
    return ResponseEntity.ok(BaseResponse.success("후기 조회 성공", responses));
  }

  @DeleteMapping("/{reviewId}")
  @Operation(summary = "후기 삭제")
  public ResponseEntity<BaseResponse<String>> deleteReview(
      @AuthenticationPrincipal CustomUserDetails userDetails,
      @PathVariable Long reviewId
  ) {
    User user = userDetails.getUser();
    reviewService.deleteReview(user, reviewId);
    return ResponseEntity.ok(BaseResponse.success("후기 삭제 완료", String.valueOf(reviewId)));
  }

}
