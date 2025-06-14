package com.likelion.springpractice.domain.review.mapper;

import com.likelion.springpractice.domain.food.entity.Food;
import com.likelion.springpractice.domain.review.dto.response.ReviewResponse;
import com.likelion.springpractice.domain.review.entity.Review;
import com.likelion.springpractice.domain.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

  public ReviewResponse toReviewResponse(Review review) {
    return ReviewResponse.builder()
        .name(review.getUser().getName())
        .foodName(review.getFood().getFoodName())
        .content(review.getContent())
        .score(review.getScore())
        .rating(review.getFood().getRating())
        .build();
  }

}
