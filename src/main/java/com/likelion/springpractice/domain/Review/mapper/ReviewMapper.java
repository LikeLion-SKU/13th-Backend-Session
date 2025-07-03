package com.likelion.springpractice.domain.Review.mapper;

import com.likelion.springpractice.domain.Review.dto.response.ReviewResponse;
import com.likelion.springpractice.domain.Review.entity.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

  public ReviewResponse toResponse(Review review) {
    return ReviewResponse.builder()
        .id(review.getId())
        .foodName(review.getFood().getName())
        .email(review.getUser().getEmail())
        .spiceRating(review.getSpiceRating())
        .content(review.getContent())
        .writtenAt(review.getWrittenAt().toString())
        .build();
  }
}
