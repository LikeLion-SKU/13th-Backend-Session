package com.likelion.springpractice.domain.food.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Schema(title = "UpdateFoodRequest: 음식 수정 요청 DTO")
public class UpdateFoodRequest {

    @NotBlank(message = "음식 이름은 비어있을 수 없습니다")
    @Schema(description = "음식 이름", example = "포도주두부무침")
    private String name;

    @NotBlank(message = "음식 설명은 비어있을 수 없습니다")
    @Schema(description = "음식 설명", example = "포도주의 상큼함을 활용한 두부 무침")
    private String description;

    @NotBlank(message = "음식 이미지는 비어있을 수 없습니다")
    @Schema(description = "음식 이미지", example = "grapedubu.png")
    private String image;
}
