package com.likelion.springpractice.domain.like.dto.response;


import com.likelion.springpractice.domain.food.dto.response.FoodResponse;
import com.likelion.springpractice.domain.food.entity.Food;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title="ChangeResponse DTO", description = "좋아요 요청에 대한 응답 변환")
public class LikeResponse {
  @Schema(description = "요청된 음식", example = "1")
  private FoodResponse food;

  @Schema(description = "요청된 좋아요 상태", example = "false")
  private boolean state;

}
