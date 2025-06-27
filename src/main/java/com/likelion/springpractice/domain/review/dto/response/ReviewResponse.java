package com.likelion.springpractice.domain.review.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "ReviewResponse", description = "리뷰 응답 DTO")
public class ReviewResponse {

  @Schema(description = "리뷰 ID", example = "1")
  private Long id;

  @Schema(description = "리뷰 대상 음식 ID", example = "5")
  private Long foodId;

  @Schema(description = "작성자 ID", example = "101")
  private Long userId;

  @Schema(description = "맵기 단계", example = "3")
  private int spicinessLevel;

  @Schema(description = "리뷰 내용", example = "생각보다 엄청 매웠어요!")
  private String content;

  @Schema(description = "작성 시간", example = "2025-06-26T18:00:00")
  private LocalDateTime createdAt;
}