package com.likelion.springpractice.week11.controller;

import com.likelion.springpractice.week11.dto.request.CreateReviewRequestDto;
import com.likelion.springpractice.week11.dto.response.ReviewResponseDto;
import com.likelion.springpractice.week11.security.CustomUserDetails;
import com.likelion.springpractice.week11.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    // 후기 작성
    @PostMapping("/reviews")
    public ResponseEntity<String> createReview(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody CreateReviewRequestDto requestDto) {

        reviewService.createReview(userDetails.getId(), requestDto);
        return ResponseEntity.ok("후기 작성 완료");
    }

    // 음식별 후기 조회
    @GetMapping("/foods/{foodId}/reviews")
    public ResponseEntity<List<ReviewResponseDto>> getReviewsByFood(@PathVariable Long foodId) {
        return ResponseEntity.ok(reviewService.getReviewsByFood(foodId));
    }

    // 후기 삭제
    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<String> deleteReview(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long reviewId) {

        reviewService.deleteReview(userDetails.getId(), reviewId);
        return ResponseEntity.ok("후기 삭제 완료");
    }
}