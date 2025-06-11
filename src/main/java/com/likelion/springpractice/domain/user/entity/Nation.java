package com.likelion.springpractice.domain.user.entity;

import io.swagger.v3.oas.annotations.media.Schema;

public enum Nation {
  @Schema(description = "한국")
  KOREA,
  @Schema(description = "미국")
  AMERICA,
  @Schema(description = "일본")
  JAPAN,
  @Schema(description = "중국")
  CHINA,
}
