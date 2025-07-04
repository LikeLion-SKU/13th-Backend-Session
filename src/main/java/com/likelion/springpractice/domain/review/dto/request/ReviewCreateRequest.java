package com.likelion.springpractice.domain.review.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(title = "ReviewCreateRequest DTO", description = "리뷰 생성용 데이터 전송")

public class ReviewCreateRequest {

  @Schema(description = "맵기 평점", example = "3")
  @Min(value = 1, message = "맵기 평점은 최소 1점이어야 합니다.")
  @Max(value = 5, message = "맵기 평점은 최대 5점이어야 합니다.")
  private Integer rating;

  @NotBlank(message = "리뷰 내용 항목은 필수입니다.")
  @Schema(description = "리뷰 내용", example = "맛있게 매워요!")
  private String content;

}
  