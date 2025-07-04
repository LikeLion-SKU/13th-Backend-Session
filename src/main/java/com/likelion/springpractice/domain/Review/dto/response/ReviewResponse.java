package com.likelion.springpractice.domain.Review.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "ReviewResponse DTO", description = "후기에 대한 응답 반환")
public class ReviewResponse {

  @Schema(description = "리뷰 ID", example = "1")
  private Long id;

  @Schema(description = "사용자 ID", example = "5")
  private Long userId;

  @Schema(description = "음식 ID", example = "10")
  private Long foodId;

  @Schema(description = "리뷰 내용", example = "맵지만 맛있어요!")
  private String content;

  @Schema(description = "맵기 정도", example = "3.5")
  private Double spicyLevel;

  @Schema(description = "리뷰 삭제 여부", example = "false")
  private Boolean isDeleted;
}
