package com.likelion.springpractice.domain.mypage.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "FavoriteFoodResponse", description = "좋아요한 음식")
public class FavoriteFoodResponse {
  @Schema(description = "음식 ID", example = "5")
  private Long foodId;

  @Schema(description = "음식 이름", example = "마라탕")
  private String foodName;
}
