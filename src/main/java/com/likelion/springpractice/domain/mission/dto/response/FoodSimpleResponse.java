package com.likelion.springpractice.domain.mission.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class FoodSimpleResponse {

  @Schema(example = "1")
  private Long foodId;

  @Schema(example = "치킨")
  private String name;

  @Schema(example = "3")
  private int likeCount;


}