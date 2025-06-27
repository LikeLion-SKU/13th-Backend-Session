package com.likelion.springpractice.domain.like.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "LikeResponse", description = "좋아요 응답 DTO")
public class LikeResponse {
  @Schema(description = "음식 ID", example = "3")
  private Long foodId;

  @Schema(description = "현재 좋아요 수", example = "15")
  private int likeCount;

  @Schema(description = "사용자 좋아요 여부", example = "true")
  private boolean liked;
}
