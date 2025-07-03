package com.likelion.springpractice.domain.badge.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@Schema(title="BadgeResponse DTO", description = "배지 요청에 대한 응답 변환")
public class BadgeResponse {
  @Schema(description = "요청된 배지 ID", example = "1")
  private Long badgeId;

  @Schema(description = "요청된 배지 이름", example = "아기배지")
  private String badgeName;

  @Schema(description = "요청된 배지 설명", example = "후기 5개 작성")
  private String badgeInformation;

  @Schema(description = "요청된 배지를 얻기 위한 후기 개수", example = "5")
  private Long count;


}
