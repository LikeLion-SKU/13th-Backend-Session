package com.likelion.springpractice.domain.like.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
@Schema(title = "LikeRequest", description = "좋아요/좋아요 취소 요청 DTO")
public class LikeRequest {
  @NotNull
  @Schema(description = "좋아요 여부 (true=좋아요, false=취소)", example = "true")
  private Boolean like;
}