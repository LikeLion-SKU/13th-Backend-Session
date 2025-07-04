package com.likelion.springpractice.domain.user.entity;

import io.swagger.v3.oas.annotations.media.Schema;

public enum Status {

  @Schema(description = "활성 상태")
  ACTIVE,
  @Schema(description = "비활성 상태")
  INACTIVE,
  @Schema(description = "삭제 상태")
  DELETED;
}
