package com.likelion.springpractice.domain.Like.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "좋아요한 음식 응답 DTO")
public class LikeFoodResponse {

  @Schema(description = "음식 ID")
  private Long foodId;

  @Schema(description = "음식 이름")
  private String foodName;

  @Schema(description = "음식 평균 맵기")
  private double averageSpice;
}

