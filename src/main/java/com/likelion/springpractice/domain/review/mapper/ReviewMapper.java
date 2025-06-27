package com.likelion.springpractice.domain.review.mapper;

import com.likelion.springpractice.domain.review.dto.response.ReviewListResponse;
import com.likelion.springpractice.domain.review.dto.response.ReviewResponse;
import com.likelion.springpractice.domain.review.entity.Review;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

  public ReviewResponse toResponse(Review r) {
    return ReviewResponse.builder()
        .id(r.getId())
        .foodId(r.getFood().getId())
        .userId(r.getUserId())
        .spicinessLevel(r.getSpicinessLevel())
        .content(r.getContent())
        .createdAt(r.getCreatedAt())
        .build();
  }

  public ReviewListResponse toListResponse(List<Review> list) {
    return ReviewListResponse.builder()
        .reviews(list.stream().map(this::toResponse).toList())
        .build();
  }
}
