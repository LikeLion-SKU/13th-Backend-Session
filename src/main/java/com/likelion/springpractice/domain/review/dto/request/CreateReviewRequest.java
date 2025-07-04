package com.likelion.springpractice.domain.review.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
@Schema(title = "CreateReviewRequest: 리뷰 생성 요청 DTO") //스웨서 문서화를 위한 어노테이션!
public class CreateReviewRequest {

  @NotNull(message = "평점은 비어 있을 수 없습니다.")
  @Min(value = 1, message = "평점은 1 이상이어야 합니다.")
  @Max(value = 5, message = "평점은 5 이하여야 합니다.")
  @Schema(description = "맵기 점수", example = "2")
  private Integer rating;

  @NotBlank(message = "내용은 비어 있을 수 없습니다.")
  @Schema(description = "리뷰 내용", example = "내용내용내용내용")
  private String comment;

}
