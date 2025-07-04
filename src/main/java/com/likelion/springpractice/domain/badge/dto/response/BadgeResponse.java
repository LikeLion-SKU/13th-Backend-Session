package com.likelion.springpractice.domain.badge.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
@Schema(title = "BadgeResponse DTO", description = "획득한 뱃지 응답")

public class BadgeResponse {

  @Schema(description = "뱃지 이름", example = "아기배찌")
  private String name;

  @Schema(description = "뱃지 설명", example = "후기 5개 달성")
  private String description;

}
  