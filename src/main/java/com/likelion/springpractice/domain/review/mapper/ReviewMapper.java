package com.likelion.springpractice.domain.review.mapper;

import com.likelion.springpractice.domain.review.dto.response.ReviewDetailResponse;
import com.likelion.springpractice.domain.review.dto.response.ReviewSummaryResponse;
import com.likelion.springpractice.domain.review.entity.Review;

public class ReviewMapper {

  public static ReviewDetailResponse toDetailResponse(Review review) {
    return ReviewDetailResponse.builder()
        .reviewId(review.getReviewId())
        .userId(review.getUser().getUserId())
        .userName(review.getUser().getName())
        .foodId(review.getFood().getId())
        .rating(review.getRating())
        .content(review.getContent())
        .build();
  }

  public static ReviewSummaryResponse toSummaryResponse(Review review) {
    return ReviewSummaryResponse.builder()
        .reviewId(review.getReviewId())
        .foodId(review.getFood().getId())
        .FoodName(review.getFood().getName())
        .rating(review.getRating())
        .build();

  }


}
