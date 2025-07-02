package com.likelion.springpractice.domain.food.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Schema(title = "CreateFoodRequest: 음식 생성 요청 DTO")
public class CreateFoodRequest {

    @NotBlank(message = "음식 이름은 비어있을 수 없습니다")
    @Schema(description = "음식 이름", example = "마라감자탕")
    private String name;

    @NotBlank(message = "음식 설명은 비어있을 수 없습니다")
    @Schema(description = "음식 설명", example = "인기있는 마라를 이용해 만든 감자탕")
    private String description;

    @NotBlank(message = "음식 이미지는 비어있을 수 없습니다")
    @Schema(description = "음식 이미지", example = "marapotata.png")
    private String image;

}
