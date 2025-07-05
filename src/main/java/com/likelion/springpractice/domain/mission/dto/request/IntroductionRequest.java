package com.likelion.springpractice.domain.mission.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "IntroductionRequest DTO", description = "자기소개 작성을 위한 데이터 전송")
public class IntroductionRequest {

  @NotBlank(message = "자기소개서 내용을 입력해주세요")
  @Schema(description = "자기소개서", example = "안녕하세요 저는 비쿠입니다.")
  private String introduction;

}
