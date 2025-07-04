package com.likelion.springpractice.domain.foodreview.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@Schema(title = "FoodReviewResponse : 음식 리뷰 응답 DTO")
public class FoodReviewResponse {

    @Schema(description = "음식 리뷰 ID", example = "1")
    private Long foodReviewId;

    @Schema(description = "음식 ID", example = "2")
    private Long foodId;

    @Schema(description = "유저 ID", example = "1")
    private Long userId;

    @Schema(description = "리뷰 제목", example = "맛있습니다.")
    private String title;

    @Schema(description = "리뷰 내용", example = "완전 맛있어용~~")
    private String content;

    @Schema(description = "리뷰 점수", example = "3.8")
    private float score;
}
