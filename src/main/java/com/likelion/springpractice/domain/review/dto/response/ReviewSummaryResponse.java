package com.likelion.springpractice.domain.review.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
@Schema(title = "ReviewSummaryResponse DTO", description = "리뷰 내용 요약 응답")

public class ReviewSummaryResponse {

  @Schema(description = "리뷰 ID", example = "1")
  private Long reviewId;

  @Schema(description = "작성자 ID", example = "1")
  private Long foodId;

  @Schema(description = "음식 이름", example = "라면")
  private String FoodName;

  @Schema(description = "맵기 평점", example = "4")
  private Integer rating;

}
  