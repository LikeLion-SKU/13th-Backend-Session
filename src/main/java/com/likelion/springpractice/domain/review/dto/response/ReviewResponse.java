package com.likelion.springpractice.domain.review.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "ReviewResponse DTO", description = "후기 내역 조회")
public class ReviewResponse {  // 근데 이거 굳이 BaseTimeEntity 써야 하나?

  @Schema(description = "후기 고유 번호", example = "1")
  private Long reviewId;

  @Schema(description = "음식에 대한 후기", example = "너무 매워요")
  private String reviewContent;

  @Schema(description = "음식에 대한 매운맛 점수", example = "4")
  private int reviewScore;

  @Schema(description = "생성일시", example = "2025-11-29T00:00:00")
  private LocalDateTime createdAt;

  @Schema(description = "수정일시", example = "2025-11-29T01:00:00")
  private LocalDateTime modifiedAt;


}
