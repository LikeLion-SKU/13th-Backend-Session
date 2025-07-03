package com.likelion.springpractice.domain.favoritefood.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "FavoriteFoodResponse DTO", description = "좋아요 결과 응답")
public class FavoriteFoodResponse {

  @Schema(description = "좋아요 상태", example = "true")
  private boolean liked;

  @Schema(description = "현재 좋아요 수", example = "42")
  private int likeCount;
}