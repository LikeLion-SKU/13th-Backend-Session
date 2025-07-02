package com.likelion.springpractice.domain.review.mapper;

import com.likelion.springpractice.domain.review.dto.response.ReviewResponse;
import com.likelion.springpractice.domain.review.entity.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

  public ReviewResponse toReviewResponse(Review review) {

    return ReviewResponse.builder()
        .reviewId(review.getReviewId())
        .reviewContent(review.getContent())
        .reviewScore(review.getScore())
        .createdAt(review.getCreatedAt())
        .modifiedAt(review.getModifiedAt())
        .build();
  }

}
