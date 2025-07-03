package com.likelion.springpractice.domain.badge.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "BadgeUserResponse DTO", description = "사용자 배지 정보 조회시 응답 변환")
public class BadgeUserResponse {

  @Schema(description = "사용자 ID", example = "1")
  private Long userId;

  @Schema(description = "배지 ID", example = "1")
  private Long badgeId;

  @Schema(description = "배지 이름", example = "아기배찌")
  String badgeName;
}
