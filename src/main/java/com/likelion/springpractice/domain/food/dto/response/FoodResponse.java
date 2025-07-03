package com.likelion.springpractice.domain.food.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
@AllArgsConstructor
@Schema(title="FoodResponse DTO", description = "음식 조회에 대한 응답 변환")
public class FoodResponse {

  @Schema(description = "요청된 음식 ID", example = "1")
  private Long foodId;

  @Schema(description = "요청된 음식명", example = "떡볶이")
  private String name;

  @Schema(description = "요청된 음식 설명", example = "가래떡을 적당한 크기로 잘라 여러 가지 채소를 넣고 양념을 하여 볶은 음식. 양념은 간장으로 하기도 하고, 고추장으로 하기도 한다.")
  private String description;

  @Schema(description = "요청된 음식 평점", example = "3.5")
  private Double rate;


}
