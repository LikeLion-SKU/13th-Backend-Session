package com.likelion.springpractice.domain.own.dto.response;


import com.likelion.springpractice.domain.badge.dto.response.BadgeResponse;
import com.likelion.springpractice.domain.badge.entity.Badge;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title="OwnResponse DTO", description = "배지 현황 요청에 대한 응답 변환")
public class OwnResponse {
  @Schema(description = "요청된 배지", example = "1")
  private BadgeResponse badge;

  @Schema(description = "요청된 배지 획득 현황 상태", example = "false")
  private boolean state;

}
