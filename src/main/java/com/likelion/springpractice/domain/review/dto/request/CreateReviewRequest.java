package com.likelion.springpractice.domain.review.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Schema(title = "CreateReviewRequest: 리뷰 생성 요청 DTO")
public class CreateReviewRequest {

  @NotBlank(message = "사용자 이름은 필수입니다.")
  @Schema(description = "사용자 이름", example = "주용")
  private String username;

  @NotBlank(message = "음식 ID는 필수입니다.")
  @Schema(description = "음식 ID", example = "1")
  private Long foodId;

  @NotBlank(message = "점수를 매겨주세요.")
  @Schema(description = "음식 점수", example = "3")
  private int score;

  @NotBlank(message = "내용은 비어 있을 수 없습니다.")
  @Schema(description = "리뷰 내용", example = "???")
  private String content;

}
