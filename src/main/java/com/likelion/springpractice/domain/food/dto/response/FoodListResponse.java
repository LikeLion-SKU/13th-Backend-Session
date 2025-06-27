package com.likelion.springpractice.domain.food.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@Schema(title = "FoodListResponse", description = "음식 목록 응답 DTO")
public class FoodListResponse {

  @Schema(description = "음식 응답 리스트")
  private List<FoodResponse> foods;
}
