package com.likelion.springpractice.domain.Review.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "리뷰 생성 및 수정 요청 DTO")
public class ReviewRequest {

  @NotBlank
  @Schema(description = "리뷰 내용", example = "맵고 맛있어요!")
  private String content;

  @NotNull
  @Schema(description = "맵기 정도", example = "4.0")
  private Double spicyLevel;
}
