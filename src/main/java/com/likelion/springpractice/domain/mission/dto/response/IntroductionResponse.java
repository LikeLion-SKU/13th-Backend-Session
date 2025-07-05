package com.likelion.springpractice.domain.mission.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "IntroductionResponse DTO", description = "자기소개 작성에 대한 응답")
public class IntroductionResponse {

  @Schema(example = "비쿠")
  private String username;

  @Schema(example = "1")
  private Long userId;

  @Schema(example = "저는 비쿠입니다.")
  private String introduction;

}
