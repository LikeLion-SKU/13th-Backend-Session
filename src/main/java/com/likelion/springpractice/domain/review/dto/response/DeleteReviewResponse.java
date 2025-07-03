package com.likelion.springpractice.domain.review.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "리뷰 삭제 응답 DTO")
public class DeleteReviewResponse {

  @Schema(description = "삭제된 리뷰 ID", example = "5")
  private Long deletedReviewId;
}
