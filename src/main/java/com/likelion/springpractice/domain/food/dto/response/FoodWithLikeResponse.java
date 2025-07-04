package com.likelion.springpractice.domain.food.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
@Schema(title = "FoodWithLikeResponse DTO", description = "음식 정보와 좋아요 정보를 함께 반환")

public class FoodWithLikeResponse {

  @Schema(description = "음식 ID", example = "1")
  private Long id;

  @Schema(description = "음식 이름", example = "떡볶이")
  private String name;

  @Schema(description = "좋아요 개수", example = "10")
  private Long likeCount;
}
  