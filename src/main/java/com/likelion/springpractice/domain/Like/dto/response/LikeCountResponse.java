package com.likelion.springpractice.domain.Like.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LikeCountResponse {

  @Schema(description = "음식 ID", example = "1")
  private Long foodId;

  @Schema(description = "좋아요 수", example = "5")
  private long likeCount;

  @Schema(description = "좋아요가 눌렸는지 여부", example = "true")
  private boolean liked;
}
