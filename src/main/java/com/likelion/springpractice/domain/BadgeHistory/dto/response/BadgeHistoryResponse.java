package com.likelion.springpractice.domain.BadgeHistory.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BadgeHistoryResponse {

  @Schema(description = "배지 히스토리 ID", example = "1")
  private Long badgeHistoryId;

  @Schema(description = "배지 이름", example = "첫 리뷰 작성")
  private String badgeName;

  @Schema(description = "배지 설명", example = "처음으로 리뷰를 작성하면 받는 배지")
  private String badgeDescription;

  @Schema(description = "회수 여부", example = "false")
  private boolean isRevoked;
}
