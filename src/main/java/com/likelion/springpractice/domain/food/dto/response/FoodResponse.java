package com.likelion.springpractice.domain.food.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "FoodResponse DTO", description = "음식 목록 및 상세 정보 조회시 응답 변환")
public class FoodResponse {


  @Schema(description = "음식 고유 ID", example = "1")
  private Long foodId;

  @Schema(description = "음식 이름", example = "신전떡볶이")
  private String foodname;

  @Schema(description = "음식 설명", example = "카레맛이 나는 떡볶이")
  private String foodDescription;

  @Schema(description = "음식에 대한 매운맛 평점", example = "4.0")
  private double avgRating;   // 음식에 대한 매운맛 평점

  @Schema(description = "음식에 대한 좋아요 수", example = "5")
  private Long likeCount;   // 음식에 대한 좋아요 수
}
