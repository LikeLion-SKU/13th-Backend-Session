package com.likelion.springpractice.domain.mypage.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "MypageReviewsReponse : 리뷰 리스트 응답 DTO")
public class MypageReviewsResponse {

  @Schema(description = "음식 ID", example = "1")
  private Long foodId;

  @Schema(description = "음식 이름", example = "김치")
  private String foodName;

  @Schema(description = "리뷰 내용", example = "???")
  private String content;
}
