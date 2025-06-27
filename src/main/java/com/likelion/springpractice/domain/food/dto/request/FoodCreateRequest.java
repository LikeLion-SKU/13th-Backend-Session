package com.likelion.springpractice.domain.food.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
@Schema(title = "FoodCreateRequest", description = "음식 생성 요청 DTO")
public class FoodCreateRequest {

  @NotBlank(message = "음식 이름은 필수입니다.")
  @Size(max = 30, message = "음식 이름은 30자 이하로 입력해야 합니다.")
  @Schema(description = "음식 이름", example = "불닭볶음면")
  private String foodName;

  @NotBlank(message = "음식 설명은 필수입니다.")
  @Size(max = 255, message = "음식 설명은 255자 이하로 입력해야 합니다.")
  @Schema(description = "음식 설명", example = "매운맛이 강한 볶음면입니다.")
  private String foodDescription;
}

