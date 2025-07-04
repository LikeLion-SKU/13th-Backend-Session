package com.likelion.springpractice.domain.food.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(title = "FoodCreateRequest DTO", description = "음식 데이터 생성을 위한 데이터 전송")

public class FoodCreateRequest {

  @NotBlank(message = "음식 이름 항목은 필수입니다.")
  @Schema(description = "음식 이름", example = "떡볶이")
  private String name;

  @NotBlank(message = "음식 설명 항목은 필수입니다.")
  @Schema(description = "음식 설명", example = "매콤한 떡볶이입니다.")
  private String description;

}
  