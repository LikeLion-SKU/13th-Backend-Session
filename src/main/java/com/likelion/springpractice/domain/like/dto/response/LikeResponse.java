package com.likelion.springpractice.domain.like.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "LikeResponse: 좋아요 응답 DTO")
public class LikeResponse {

  @Schema(description = "음식 이름", example = "1")
  private String foodName;

  @Schema(description = "사용자 이름", example = "주용쓰")
  private String name;

  @Schema(description = "좋아요 상태", example = "true")
  private boolean status;

  @Schema(description = "음식 좋아요 수", example = "1")
  private int LikeNum;

}
