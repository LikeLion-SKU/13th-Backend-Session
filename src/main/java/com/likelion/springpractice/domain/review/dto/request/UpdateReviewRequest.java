package com.likelion.springpractice.domain.review.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(title = "UpdateReviewRequest DTO", description = "후기 수정 요청")
public class UpdateReviewRequest {

  @Schema(description = "수정할 후기 내용", example = "매워서 응급실 실려감..")
  private String content;

  @Schema(description = "수정할 매운맛 점수 (0~5)", example = "5")
  private int score;

  // 또 수정일시 필요 없
}
