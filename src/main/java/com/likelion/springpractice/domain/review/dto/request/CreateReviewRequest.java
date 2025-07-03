package com.likelion.springpractice.domain.review.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Schema(description = "CreateReviewRequest DTO")
public class CreateReviewRequest {

  @Min(value = 1, message = "맵기는 최소 1단계 이상이어야 합니다.")
  @Max(value = 5, message = "맵기는 최대 5단계 이하여야 합니다.")
  @Schema(description = "맵기 정도 (1~5단계)", example = "3")
  private int spicyLevel;

  @NotBlank(message = "후기를 작성해주세요.")
  @Schema(description = "후기 코멘트", example = "생각보다 많이 맵지만 맛있어요!")
  private String comment;

}
