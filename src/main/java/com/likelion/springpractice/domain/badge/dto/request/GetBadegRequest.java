package com.likelion.springpractice.domain.badge.dto.request;

import com.likelion.springpractice.domain.badge.entity.BadgeName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(title = "GetBadgeRequest: 사용자 보유 배찌 조회 요청 DTO")
public class GetBadegRequest {

  @Schema(description = "배찌 이름", example = "아기 배찌")
  private BadgeName badgeName;

}
