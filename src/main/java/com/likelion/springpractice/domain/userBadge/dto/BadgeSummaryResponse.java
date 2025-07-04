package com.likelion.springpractice.domain.userBadge.dto;

import com.likelion.springpractice.domain.userBadge.entity.UserBadge;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(title = "BadgeSummaryResponse: 획득한 배찌 전체 조회 응답 DTO") //스웨서 문서화를 위한 어노테이션!
public class BadgeSummaryResponse {

  @Schema(description = "배찌 아이디", example = "1")
  private Long badgeId;

  @Schema(description = "배지 이름", example = "아기사자 배찌")
  private String name;

  public BadgeSummaryResponse(UserBadge userBadge) {
    this.badgeId = userBadge.getBadge().getId();
    this.name = userBadge.getBadge().getName();
  }
}
