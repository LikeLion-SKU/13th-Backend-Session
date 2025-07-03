package com.likelion.springpractice.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Schema(title = "UpdateLanguageRequest DTO", description = "언어 변경 요청")
public class UpdateLanguageRequest {

  @NotBlank(message = "국적은 필수입니다.")
  @Schema(description = "국적", example = "Korean")
  private String language;

}
