package com.likelion.springpractice.domain.food.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
@Schema(title = "FoodSummaryResponse DTO", description = "음식 목록 응답 반환")

public class FoodSummaryResponse {

  @Schema(description = "음식 ID", example = "1")
  private Long id;

  @Schema(description = "음식 이름", example = "떡볶이")
  private String name;

}
  