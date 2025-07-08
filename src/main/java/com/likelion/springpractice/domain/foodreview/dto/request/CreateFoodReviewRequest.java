package com.likelion.springpractice.domain.foodreview.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
@Schema(title = "CreateFoodReviewRequest: 리뷰 생성 요청 DTO")
public class CreateFoodReviewRequest {

    @NotBlank(message = "제목은 비어 있을 수 없습니다.")
    @Schema(description = "리뷰 제목", example = "너무 맛있어요")
    private String title;

    @NotBlank(message = "내용은 비어 있을 수 없습니다.")
    @Schema(description = "리뷰 내용", example = "제가 살면서 먹어본 음식 중에 제일 맛있어요")
    private String content;

    @NotNull(message = "점수는 비어있을 수 없습니다.")
    @Schema(description = "리뷰 점수", example = "4.3")
    private float score;

}
