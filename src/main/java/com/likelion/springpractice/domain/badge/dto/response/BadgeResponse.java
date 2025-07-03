package com.likelion.springpractice.domain.badge.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "BadgeResponse", description = "획득한 뱃지 정보")
public class BadgeResponse {

  @Schema(description = "뱃지 이름", example = "아기배찌")
  private String name;

  @Schema(description = "뱃지 설명", example = "후기 5개 작성 시 획득")
  private String description;
}
