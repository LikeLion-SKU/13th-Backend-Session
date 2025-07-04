package com.likelion.springpractice.domain.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "UpdateLanguageResponse DTO", description = "국적 변경에 대한 응답 반환")
public class UpdateLanguageResponse {

  @Schema(description = "사용자 ID", example = "1")
  private String username;

  @Schema(description = "변경된 국적", example = "Korean")
  private String language;
}