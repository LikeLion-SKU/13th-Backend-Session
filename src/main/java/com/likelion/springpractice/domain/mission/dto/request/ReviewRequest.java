package com.likelion.springpractice.domain.mission.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "ReviewRequest DTO", description = "리뷰 입력을 받고 데이터 전송")
public class ReviewRequest {

  @NotNull(message = "음식 ID는 필수입니다.")
  private Long foodId;

  @NotBlank(message = "내용은 비어있을 수 없습니다.")
  private String content;

  @Min(1)
  @Max(5)
  private int rating;

}
