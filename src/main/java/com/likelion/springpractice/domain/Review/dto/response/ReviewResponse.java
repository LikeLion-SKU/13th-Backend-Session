package com.likelion.springpractice.domain.Review.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewResponse {

  private Long id;
  private String foodName;
  private String email;
  private double spiceRating;
  private String content;
  private String writtenAt;
}
