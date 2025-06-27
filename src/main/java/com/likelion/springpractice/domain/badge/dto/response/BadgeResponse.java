package com.likelion.springpractice.domain.badge.dto.response;

import com.likelion.springpractice.domain.badge.entity.BadgeName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "배지 응답 DTO")
public class BadgeResponse {

  @Schema(description = "배지 이름", example = "BABY_BADGE")
  private BadgeName badgeName;

  @Schema(description = "배지 획득에 필요한 리뷰 수", example = "5")
  private int requiredReviews;

  @Schema(description = "사용자가 현재 보유한 개수", example = "1")
  private int ownedCount;
}