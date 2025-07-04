package com.likelion.springpractice.domain.review.mapper;

import com.likelion.springpractice.domain.review.dto.response.CreateReviewResponse;
import com.likelion.springpractice.domain.review.dto.response.UpdateReviewResponse;
import com.likelion.springpractice.domain.review.entity.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

  public CreateReviewResponse toCreateReviewResponse(Review review, int reviewCount) {
    return CreateReviewResponse.builder()
        .username(review.getUser().getUsername())
        .foodId(review.getFood().getId())
        .spicyLevel(review.getSpicyLevel())
        .comment(review.getComment())
        .reviewCount(reviewCount)
        .build();
  }

  public UpdateReviewResponse toUpdateReviewResponse(Review review) {
    return UpdateReviewResponse.builder()
        .reviewId(review.getId())
        .spicyLevel(review.getSpicyLevel())
        .comment(review.getComment())
        .build();
  }
}