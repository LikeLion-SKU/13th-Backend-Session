package com.likelion.springpractice.domain.like.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(title = "LikeRequest: 좋아요 요청 DTO")
public class LikeRequest {

  @Schema(description = "음식 Id", example = "1")
  private Long foodId;


}
