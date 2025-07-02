package com.likelion.springpractice.domain.review.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(title = "CreateReviewRequest DTO", description = "후기 생성 요청")
public class CreateReviewRequest {

  @Schema(description = "후기 대상 음식 ID", example = "1")
  private Long foodId;  // 후기는 음식에 종속됨 -> 무조건 음식 ID 필요

  @Schema(description = "후기 내용", example = "단맵단맵")
  private String reviewContent;

  @Schema(description = "매운맛 점수 (0~5)", example = "4")
  private int reviewScore;

}
