package com.likelion.springpractice.domain.review.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "CreateReviewResponse DTO", description = "리뷰 작성에 대한 응답")
public class CreateReviewResponse {
  
  @Schema(description = "작성자 이름", example = "채린")
  private String username;

  @Schema(description = "리뷰 대상 음식 ID", example = "12")
  private Long foodId;

  @Schema(description = "맵기 정도", example = "4")
  private int spicyLevel;

  @Schema(description = "리뷰 코멘트", example = "진짜 맛있어요! 또 먹고 싶음.")
  private String comment;

  @Schema(description = "리뷰 갯수", example = "4")
  private int reviewCount;

}
