package com.likelion.springpractice.domain.review.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title="ReviewRequest DTO", description = "후기 작성을 위한 데이터 전송 ")
public class ReviewRequest {

  @NotNull(message = "음식 항목은 필수입니다.")
  @Schema(description = "음식 고유 ID", example = "1")
  private Long foodId;

  @NotNull(message = "평점 항목은 필수입니다.")
  @Schema(description = "매긴 음식 평점", example = "3.5")
  private Double rate;

  @NotBlank(message = "후기 내용 항목은 필수입니다.")
  @Schema(description = "후기 내용", example = "맛있게 매워요.")
  private String content;


}
