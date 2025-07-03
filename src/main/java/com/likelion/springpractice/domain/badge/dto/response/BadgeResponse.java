package com.likelion.springpractice.domain.badge.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "BatchResponse DTO", description = "배지 정보 조회시 응답 변환")
public class BatchResponse {

  @Schema(description = "배지 ID", example = "1")
  private Long batchId;

  @Schema(description = "배지 이름", example = "아기배찌")
  private String batchName;

  @Schema(description = "후기 최소 개수", example = "5")
  private int minReview;

  @Schema(description = "후기 최대 개수", example = "9")
  private int maxReview;
}
