package com.likelion.springpractice.domain.review.mapper;

import com.likelion.springpractice.domain.review.dto.response.ReviewResponse;
import com.likelion.springpractice.domain.review.entity.Review;
import com.likelion.springpractice.domain.user.dto.response.SignUpResponse;
import com.likelion.springpractice.domain.user.entity.User;
import org.springframework.stereotype.Component;


@Component
public class ReviewMapper {


  public ReviewResponse toReviewResponse(Review review) {
    return ReviewResponse.builder()
        .reviewId(review.getId())
        .username(review.getUser().getUsername())
        .name(review.getUser().getName())
        .foodId(review.getFood().getId())
        .foodName(review.getFood().getName())
        .rate(review.getRate())
        .content(review.getContents())
        .build();
  }

}
