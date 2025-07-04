package com.likelion.springpractice.domain.review.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
@Schema(title = "ReviewDetailResponse DTO", description = "리뷰 내용 응답 반환")

public class ReviewDetailResponse {

  @Schema(description = "리뷰 ID", example = "1")
  private Long reviewId;

  @Schema(description = "작성자 ID", example = "1")
  private Long userId;

  @Schema(description = "작성자 이름", example = "재연")
  private String userName;

  @Schema(description = "음식 ID", example = "1")
  private Long foodId;

  @Schema(description = "평점", example = "5")
  private Integer rating;

  @Schema(description = "리뷰 내용", example = "정말 맛있어요!")
  private String content;

}
  