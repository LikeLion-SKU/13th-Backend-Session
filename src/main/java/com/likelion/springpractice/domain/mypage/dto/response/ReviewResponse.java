package com.likelion.springpractice.domain.mypage.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "ReviewResponse", description = "작성한 리뷰 정보")
public class ReviewResponse {

  @Schema(description = "리뷰 ID", example = "10")
  private Long reviewId;

  @Schema(description = "리뷰 대상 음식 이름", example = "떡볶이")
  private String foodName;

  @Schema(description = "맵기 평점", example = "3")
  private int spicyLevel;

  @Schema(description = "리뷰 코멘트", example = "맵고 맛있어요!")
  private String comment;
}
