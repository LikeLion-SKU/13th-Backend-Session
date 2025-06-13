package com.likelion.springpractice.domain.food.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(title = "GetFoodRequest: 음식 조회 요청 DTO")
public class GetFoodRequest {

  @Schema(description = "음식 이름", example = "김치")
  private String foodName;

  @Schema(description = "음식 내용", example = "???")
  private String description;

  @Schema(description = "평점", example = "4.5")
  private Double rating;

  @Schema(description = "좋아요 수", example = "5")
  private int likeNum;

  @Schema(description = "리뷰 수", example = "5")
  private String reviewNum;

}
