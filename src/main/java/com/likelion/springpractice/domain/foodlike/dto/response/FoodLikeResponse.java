package com.likelion.springpractice.domain.foodlike.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "FoodLikeResponse : 음식 좋아요 응답 DTO")
public class FoodLikeResponse {

    @Schema(description = "음식 좋아요 ID", example = "1")
    private Long foodlikeId;

    @Schema(description = "음식 ID", example = "2")
    private Long foodId;

    @Schema(description = "유저 ID", example = "1")
    private Long userId;

}
