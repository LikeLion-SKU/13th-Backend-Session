package com.likelion.springpractice.domain.like.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
@AllArgsConstructor
@Schema(title = "LikeResponse DTO", description = "좋아요 등록 또는 취소에 대한 응답")
public class LikeResponse {

  @Schema(description = "음식 Id", example = "1")
  private Long foodId;

  @Schema(description = "좋아요 여부", example = "true")
  private boolean liked;

  @Schema(description = "좋아요 개수", example = "10")
  private Long likeCount;

}
  