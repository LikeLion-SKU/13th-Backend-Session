package com.likelion.springpractice.domain.Review.controller;

import com.likelion.springpractice.domain.Review.dto.request.ReviewRequest;
import com.likelion.springpractice.domain.Review.dto.response.ReviewResponse;
import com.likelion.springpractice.domain.Review.service.ReviewService;
import com.likelion.springpractice.global.Response.BaseResponse;
import com.likelion.springpractice.global.Security.CustomUserDetails;
import com.likelion.springpractice.global.exception.CustomException;
import com.likelion.springpractice.global.exception.GlobalErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
@Tag(name = "Review", description = "후기 관련 API")
public class ReviewController {

  private final ReviewService reviewService;

  @Operation(summary = "후기 수정 API", description = "자신의 후기 수정을 위한 API")
  @PutMapping("/{reviewId}/update")
  public ResponseEntity<BaseResponse<ReviewResponse>> updateReview(
      @Parameter(hidden = true)
      @AuthenticationPrincipal CustomUserDetails userDetails,
      @Parameter(description = "특정 후기 ID")
      @PathVariable Long reviewId,
      @RequestBody ReviewRequest reviewRequest) {
    if (userDetails == null) {
      throw new CustomException(GlobalErrorCode.UNAUTHORIZED);
    }

    ReviewResponse response
        = reviewService.updateReview(userDetails.getUser().getId(), reviewId, reviewRequest);
    return ResponseEntity.ok(BaseResponse.success("후기 수정에 성공했습니다.", response));
  }

  // 후기 복구는 따로 만들지 않음.
  // 추후 관리자 페이지에서 후기 복구를 만들면 될듯 (role='ADMIN'으로 접근 제한 걸고)

  @Operation(summary = "후기 삭제 API", description = "자신의 후기를 삭제를 위한 API")
  @PutMapping("/{reviewId}/delete")
  public ResponseEntity<BaseResponse<String>> deleteReview(
      @Parameter(hidden = true)
      @AuthenticationPrincipal CustomUserDetails userDetails,
      @Parameter(description = "삭제할 후기 ID")
      @PathVariable Long reviewId) {
    if (userDetails == null) {
      throw new CustomException(GlobalErrorCode.UNAUTHORIZED);
    }

    reviewService.deleteReview(userDetails.getUser().getId(), reviewId);
    return ResponseEntity.ok(BaseResponse.success("후기 삭제에 성공했습니다."));
  }

  @Operation(summary = "후기 목록 조회 API", description = "자신의 후기 목록을 조회하기 위한 API")
  @GetMapping("/")
  public ResponseEntity<BaseResponse<List<ReviewResponse>>> getAllReviews(
      @Parameter(hidden = true)
      @AuthenticationPrincipal CustomUserDetails userDetails) {
    if (userDetails == null) {
      throw new CustomException(GlobalErrorCode.UNAUTHORIZED);
    }

    List<ReviewResponse> responses = reviewService.getAllReviews(userDetails.getUser().getId());

    return ResponseEntity.ok(BaseResponse.success("후기 목록 조회에 성공했습니다.", responses));
  }
}
