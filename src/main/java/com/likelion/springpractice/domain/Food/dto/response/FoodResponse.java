package com.likelion.springpractice.domain.Food.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "FoodResponse DTO", description = "음식 대한 응답 반환")
public class FoodResponse {

  @Schema(description = "음식 ID", example = "1")
  private Long id;

  @Schema(description = "음식 이름", example = "마라탕")
  private String name;

  @Schema(description = "음식 설명", example = "얼얼하고 매운 국물 요리")
  private String description;

  @Schema(description = "좋아요 수", example = "125")
  private int likeCount;

  @Schema(description = "평균 매운맛 수치", example = "2.5")
  private double spicyLevelAvg;
}
