package com.likelion.springpractice.domain.review.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
@Schema(description = "리뷰 수정 요청 DTO")
public class UpdateReviewRequest {

  @NotNull(message = "맵기 평점은 필수입니다.")
  @Schema(description = "맵기 평점", example = "3")
  private Integer spicyLevel;

  @NotBlank(message = "리뷰 코멘트는 필수입니다.")
  @Schema(description = "리뷰 코멘트", example = "맛있게 매워요.")
  private String comment;
}