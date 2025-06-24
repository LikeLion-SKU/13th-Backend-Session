package com.likelion.springpractice.domain.badge.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum BadgeName {
  @Schema(description = "후기 5개 달성 시 지급")
  BABYBADGE(5),
  @Schema(description = "후기 15개 달성 시 지급")
  SPOONBADGE(15),
  @Schema(description = "후기 30개 달성 시 지급")
  CUTLERYBADGE(30),
  @Schema(description = "후기 50개 달성 시 지급")
  SERVERBADGE(50);

  private final int requiredReviews; // 배찌 지급에 필요한 후기 개수

}
