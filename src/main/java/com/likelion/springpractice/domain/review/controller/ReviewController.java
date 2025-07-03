package com.likelion.springpractice.domain.review.controller;

import com.likelion.springpractice.domain.review.dto.request.CreateReviewRequest;
import com.likelion.springpractice.domain.review.dto.request.UpdateReviewRequest;
import com.likelion.springpractice.domain.review.dto.response.ReviewResponse;
import com.likelion.springpractice.domain.review.service.ReviewService;
import com.likelion.springpractice.domain.user.entity.User;
import com.likelion.springpractice.global.response.BaseResponse;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2")
@Tag(name = "review", description = "후기 관련 API")
public class ReviewController {

  private final ReviewService reviewService;

  // 후기 생성 API
  @PostMapping
  @Operation(summary = "후기 생성", description = "후기 생성 후 생성버튼을 눌렀을때 요청되는 API")
  public ResponseEntity<BaseResponse<ReviewResponse>> createReview(
      @Parameter(description = "후기 작성 내용")
      @RequestBody @Valid CreateReviewRequest createReviewRequest,
      @AuthenticationPrincipal User user) {
    ReviewResponse response = reviewService.createReview(createReviewRequest, user);
    return ResponseEntity.ok(BaseResponse.success("후기 생성 성공", response));
  }

  // 후기 수정 API
  @Operation(summary = "후기 수정",
      description = "후기 수정 후 수정 완료 버튼을 눌렀을때 요청되는 API")
  @PutMapping("/reviews/{reviewId}")
  public ResponseEntity<BaseResponse<ReviewResponse>> updateReview(
      @Parameter(description = "후기 수정 내용") @RequestBody UpdateReviewRequest updateReviewRequest,
      @Parameter(description = "특정 후기 ID") @PathVariable Long reviewId) {
    ReviewResponse response = reviewService.updateReview(reviewId,
        updateReviewRequest); // 수정한 게시글 반환
    return ResponseEntity.ok(BaseResponse.success("후기 수정 성공", response));
  }

  // 후기 삭제 API
  @Operation(summary = "후기 삭제", description = "후기 삭제 버튼을 눌렀을때 요청되는 API")
  @DeleteMapping("/reviews/{reviewId}")
  public ResponseEntity<BaseResponse<Boolean>> deleteReview(
      @Parameter(description = "특정 후기 ID") @PathVariable Long reviewId,
      @AuthenticationPrincipal User user) {
    boolean isDeleted = reviewService.deleteReview(reviewId, user);
    return ResponseEntity.ok(BaseResponse.success("후기 삭제 성공", isDeleted));
  }

  // 사용자별 후기 리스트 조회 API
  @Operation(summary = "마이페이지 후기 내역 조회", description = "마이페이지에서 후기 내역 조회 시 요청되는 API")
  @GetMapping("/reviews")
  public ResponseEntity<BaseResponse<List<ReviewResponse>>> getReviewsByUser(
      @AuthenticationPrincipal User user) {
    List<ReviewResponse> reviewList = reviewService.getReviewsByUser(user);
    return ResponseEntity.ok(BaseResponse.success("사용자별 후기 조회 완료", reviewList));
  }

  // 음식별 후기 리스트 조회 API
  @Operation(summary = "음식별 후기 내역 조회", description = "음식 상세 페이지에서 후기 내역 조회 시 요청되는 API")
  @GetMapping("/reviews")
  public ResponseEntity<BaseResponse<List<ReviewResponse>>> getReviewsByFood(
      @PathVariable Long foodId) {
    List<ReviewResponse> reviewList = reviewService.getReviewsByFood(foodId);
    return ResponseEntity.ok(BaseResponse.success("음식별 후기 조회 완료", reviewList));
  }

}
