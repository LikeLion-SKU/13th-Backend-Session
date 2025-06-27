package com.likelion.springpractice.domain.review.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
@Schema(title = "ReviewCreateRequest", description = "리뷰 생성 요청 DTO")
public class ReviewCreateRequest {

  @Min(value = 1, message = "맵기 단계는 최소 1 이상이어야 합니다.")
  @Max(value = 5, message = "맵기 단계는 최대 5 이하여야 합니다.")
  @Schema(description = "맵기 단계 (1~5)", example = "3")
  private int spicinessLevel;

  @NotBlank(message = "리뷰 내용은 필수입니다.")
  @Size(max = 255, message = "리뷰 내용은 255자 이하로 작성해야 합니다.")
  @Schema(description = "리뷰 내용", example = "생각보다 매우 맵네요!")
  private String content;
}
