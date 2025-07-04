package com.likelion.springpractice.domain.user.entity;

import io.swagger.v3.oas.annotations.media.Schema;

public enum Nation {

  @Schema(description = "한국어")
  KO,
  @Schema(description = "영어")
  EN;
}
