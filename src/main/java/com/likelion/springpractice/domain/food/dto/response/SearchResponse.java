package com.likelion.springpractice.domain.food.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title="SearchResponse DTO", description = "음식 검색에 대한 응답 변환")
public class SearchResponse {
  @Schema(description = "검색된 음식 ID", example = "1")
  private Long foodId;

  @Schema(description = "검색된 음식명", example = "떡볶이")
  private String name;

  @Schema(description = "검색된 음식 평점", example = "3.5")
  private Double rate;

}
