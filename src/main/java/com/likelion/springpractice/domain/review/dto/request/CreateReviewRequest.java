package com.likelion.springpractice.domain.review.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
@Schema(title = "CreateReviewRequest: 리뷰 생성 요청 DTO")
public class CreateReviewRequest {

  @NotNull(message = "음식 ID는 필수입니다.")
  @Schema(description = "음식 ID", example = "1")
  private Long foodId;

  @Min(value = 0, message = "점수는 최소 0점 이상이어야 합니다.")
  @Max(value = 5, message = "점수는 최대 5점 이하여야 합니다.")
  @Schema(description = "음식 점수", example = "3")
  private int score;

  @NotBlank(message = "내용은 비어 있을 수 없습니다.")
  @Schema(description = "리뷰 내용", example = "???")
  private String content;

}
