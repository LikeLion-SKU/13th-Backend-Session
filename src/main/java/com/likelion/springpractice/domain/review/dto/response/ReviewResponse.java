package com.likelion.springpractice.domain.review.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "ReviewResponse: 리뷰 응답 DTO")
public class ReviewResponse {

  @Schema(description = "리뷰 작성자 이름", example = "주용")
  private String name;

  @Schema(description = "음식 이름", example = "김치")
  private String foodName;

  @Schema(description = "리뷰 내용", example = "???")
  private String content;

  @Schema(description = "음식 점수", example = "3")
  private int score;

  @Schema(description = "음식 평점", example = "3.0")
  private double rating;


}
