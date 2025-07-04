package com.likelion.springpractice.domain.like.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
@AllArgsConstructor
@Schema(title = "LikedFoodResponse DTO", description = "좋아요한 음식 리스트 응답")
public class LikedFoodResponse {

  @Schema(description = "음식 Id", example = "1")
  private Long foodId;

  @Schema(description = "음식 이름", example = "라면")
  private String foodName;


}
  