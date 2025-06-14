package com.likelion.springpractice.domain.review.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Schema(title = "GetReviewRequest: 리뷰 조회 요청 DTO")
public class GetReviewRequest {

  @Schema(description = "음식 이름", example = "김치")
  private int foodName;

  @Schema(description = "음식 리뷰 작성자", example = "주용")
  private int username;

  @Schema(description = "리뷰 내용", example = "???")
  private String content;

  @Schema(description = "음식 점수", example = "3")
  private int score;

  @Schema(description = "음식 평점", example = "3.0")
  private double rating;

}
