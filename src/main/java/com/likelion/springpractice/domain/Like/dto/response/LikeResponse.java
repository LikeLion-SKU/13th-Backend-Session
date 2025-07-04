package com.likelion.springpractice.domain.Like.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "LikeResponse DTO", description = "좋아요에 대한 응답 반환")
public class LikeResponse {

  @Schema(description = "좋아요 ID", example = "1")
  private Long id;

  @Schema(description = "음식 ID", example = "10")
  private Long foodId;

  @Schema(description = "음식 이름", example = "김치찌개")
  private String foodName;

  @Schema(description = "좋아요 여부", example = "false")
  private Boolean isDeleted;
}
