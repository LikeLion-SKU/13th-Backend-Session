package com.likelion.springpractice.domain.food.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
@Schema(title = "FoodDetailResponse DTO", description = "음식 상세 정보 응답")

public class FoodDetailResponse {

  @Schema(description = "음식 ID", example = "1")
  private Long id;

  @Schema(description = "음식 이름", example = "떡볶이")
  private String name;

  @Schema(description = "음식 설명", example = "매콤한 떡볶이입니다.")
  private String description;


}
  