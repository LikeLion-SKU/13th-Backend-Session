package com.likelion.springpractice.domain.Badge.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BadgeResponse {

  @Schema(description = "배지 ID", example = "1")
  private Long badgeId;

  @Schema(description = "배지 이름", example = "첫 리뷰 작성")
  private String badgeName;

  @Schema(description = "배지 설명", example = "처음으로 리뷰를 작성하면 받는 배지")
  private String description;

  @Schema(description = "배지 획득을 위한 후기 개수 조건", example = "10")
  private Integer unlock_count;
}
