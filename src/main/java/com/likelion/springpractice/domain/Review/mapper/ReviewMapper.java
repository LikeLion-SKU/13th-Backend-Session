package com.likelion.springpractice.domain.Review.mapper;

import com.likelion.springpractice.domain.Review.dto.response.ReviewResponse;
import com.likelion.springpractice.domain.Review.entity.Review;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

  public ReviewResponse toReviewResponse(Review review) {
    return ReviewResponse.builder()
        .id(review.getId())
        .userId(review.getUser().getId())
        .foodId(review.getFood().getId())
        .content(review.getContent())
        .spicyLevel(review.getSpicyLevel())
        .isDeleted(review.getIsDeleted())
        .build();
  }

  public List<ReviewResponse> toReviewResponseList(List<Review> reviewList) {
    return reviewList.stream()
        .map(this::toReviewResponse)
        .toList();
  }
}
