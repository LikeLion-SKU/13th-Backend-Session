package com.likelion.springpractice.domain.review.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "ReviewResponse: 리뷰 응답 DTO")
public class ReviewResponse {

  @Schema(description = "리뷰 ID", example = "1")
  private Long reviewId;

  @Schema(description = "리뷰 내용", example = "이거 우유 없이는 못 먹어요!")
  private String comment;

  @Schema(description = "맵기 평점", example = "3")
  private Integer rating;

  @Schema(description = "리뷰 작성자 아이디", example = "hamni0531")
  private String username;

  @Schema(description = "작성 일자", example = "")
  private LocalDateTime createdAt;

}
