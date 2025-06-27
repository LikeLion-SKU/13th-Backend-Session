package com.likelion.springpractice.domain.food.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "FoodResponse", description = "음식 단일 응답 DTO")
public class FoodResponse {

  @Schema(description = "음식 ID", example = "1")
  private Long id;

  @Schema(description = "음식 이름", example = "불닭볶음면")
  private String foodName;

  @Schema(description = "음식 설명", example = "매운맛이 강한 볶음면입니다.")
  private String foodDescription;

  @Schema(description = "좋아요 수", example = "42")
  private int likeCount;

  @Schema(description = "리뷰 수", example = "7")
  private int reviewCount;
}