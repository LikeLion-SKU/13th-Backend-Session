package com.likelion.springpractice.domain.food.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "FoodDetailResponse DTO", description = "음식 세부내용 반환")
public class FoodDetailResponse {
  private String foodName;
  private String description;
}