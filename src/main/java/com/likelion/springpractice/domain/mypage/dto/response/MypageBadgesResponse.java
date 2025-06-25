package com.likelion.springpractice.domain.mypage.dto.response;

import com.likelion.springpractice.domain.badge.entity.BadgeName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "MypageBadgesResponse: 보유 배찌 응답 DTO")
public class MypageBadgesResponse {

  @Schema(description = "배찌 이름", example = "BABYBADGE")
  private BadgeName badgeName;

  @Schema(description = "배찌 개수", example = "1")
  private int badgeNum;

}
