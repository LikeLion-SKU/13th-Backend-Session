package com.likelion.springpractice.domain.food.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "FoodResponse : 음식 응답 DTO")
public class FoodResponse {

    @Schema(description = "음식 ID", example = "1")
    private Long foodId;

    @Schema(description = "음식 이름", example = "순두부찌개")
    private String name;

    @Schema(description = "음식 설명", example = "순두부가 듬뿍 들어간 순두부찌개")
    private String description;

    @Schema(description = "음식 사진", example = "image3.png")
    private String image;

    @Schema(description = "음식 평점", example = "4.4")
    private float score;

    @Schema(description = "음식 좋아요 수", example = "3")
    private int likes;

}
