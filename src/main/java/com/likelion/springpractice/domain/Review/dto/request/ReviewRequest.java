package com.likelion.springpractice.domain.Review.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
@Schema(description = "후기 작성 요청 DTO")
public class ReviewRequest {

  @NotNull
  @Schema(description = "음식 ID", example = "1")
  private Long foodId;

  @DecimalMin(value = "0.0")
  @DecimalMax(value = "5.0")
  @Schema(description = "맵기 점수 (0.0 ~ 5.0)", example = "4.5")
  private double spiceRating;

  @NotBlank
  @Schema(description = "후기 내용", example = "매콤하고 맛있어요!")
  private String content;
}
