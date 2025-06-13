package com.likelion.springpractice.domain.food.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Schema(title = "CreateFoodRequest: 음식 생성 요청 DTO")
public class CreateFoodRequest {

  @NotBlank(message = "음식 이름은 비어 있을 수 없습니다.")
  @Schema(description = "음식 이름", example = "김치")
  private String foodName;

  @NotBlank(message = "음식 설명은 비어 있을 수 없습니다.")
  @Schema(description = "음식 설명", example = "설명설명설명")
  private String description;

}
