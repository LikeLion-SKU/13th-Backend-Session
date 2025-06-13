package com.likelion.springpractice.domain.food.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "FoodResponse: 음식 응답 DTO")
public class FoodResponse {

  @Schema(description = "음식 ID", example = "1")
  private Long food_id;

  @Schema(description = "음식 이름", example = "김치")
  private String foodName;

  @Schema(description = "음식 설명", example = "???")
  private String description;

  @Schema(description = "평점", example = "4.5")
  private Double rating;

  @Schema(description = "좋아요 수", example = "5")
  private int likeNum;

  @Schema(description = "후기 수", example = "5")
  private int revirewNum;

}
