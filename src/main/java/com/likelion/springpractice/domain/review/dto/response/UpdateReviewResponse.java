package com.likelion.springpractice.domain.review.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "UpdateReviewResponse DTO", description = "리뷰 수정 응답")
public class UpdateReviewResponse {

  @Schema(description = "리뷰 ID", example = "10")
  private Long reviewId;

  @Schema(description = "수정된 맵기 정도", example = "4")
  private int spicyLevel;

  @Schema(description = "수정된 리뷰 코멘트", example = "좀 더 맵지만 여전히 맛있어요!")
  private String comment;
}